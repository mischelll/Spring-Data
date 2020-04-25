package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.constants.GlobalConstants.*;

@Service
public class CarServiceImpl implements CarService {
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final Gson gson;

    public CarServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, CarRepository carRepository, Gson gson) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        String json = "";

        try {
            json = String.join("\n", Files.readAllLines(Paths.get(CARS_PATH_JSON)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return json;
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder result = new StringBuilder();

        CarSeedDto[] carSeedDtos = this.gson.fromJson(new FileReader(CARS_PATH_JSON), CarSeedDto[].class);
        System.out.println();
        Arrays.stream(carSeedDtos).forEach(carSeedDto -> {
            if (this.validationUtil.isValid(carSeedDto)) {
                if (this.carRepository.findByKilometers(carSeedDto.getKilometers()) == null &&
                        this.carRepository.findByMake(carSeedDto.getMake()) == null &&
                        this.carRepository.findByModel(carSeedDto.getModel()) == null) {

                    LocalDate date = LocalDate.parse(carSeedDto.getRegisteredOn(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Car map = this.modelMapper.map(carSeedDto, Car.class);
                    map.setRegisteredOn(date);

                    this.carRepository.saveAndFlush(map);
                    result.append(String.format(SUCCESSFULL_IMPORT_CAR, map.getMake(), map.getModel()))
                            .append(System.lineSeparator());
                }
            } else {
                result.append(String.format(UNSUCCESSFULL_IMPORT, Car.class
                        .getSimpleName()
                        .toLowerCase()))
                        .append(System.lineSeparator());
            }
        });
        return result.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Car> all = this.carRepository.findAll();
        List<Car> collect = all.stream().sorted((a, b) -> {
            int sort = b.getPictures().size() - a.getPictures().size();
            if (sort == 0) {
                sort = a.getMake().compareTo(b.getMake());
            }
            return sort;
        }).collect(Collectors.toList());
        for (Car car : collect) {
            stringBuilder.append(String.format("Car make - %s, model - %s\n" +
                    "\tKilometers - %s\n" +
                    "\tRegistered on - %s\n" +
                    "\tNumber of pictures - %d\n",car.getMake(),car.getModel(),car.getKilometers(),car.getRegisteredOn().toString(),car.getPictures().size())).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
