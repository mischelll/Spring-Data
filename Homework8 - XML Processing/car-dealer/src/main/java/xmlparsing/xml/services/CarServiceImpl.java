package xmlparsing.xml.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.cars.*;
import xmlparsing.xml.domain.dtos.parts.PartViewDto;
import xmlparsing.xml.domain.dtos.parts.PartViewRootDto;
import xmlparsing.xml.domain.entities.Car;
import xmlparsing.xml.domain.entities.Part;
import xmlparsing.xml.repositories.CarRepository;
import xmlparsing.xml.services.interfaces.CarService;
import xmlparsing.xml.services.interfaces.PartService;
import xmlparsing.xml.services.interfaces.SupplierService;
import xmlparsing.xml.utils.interfaces.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;

import java.util.*;
import java.util.stream.Collectors;

import static xmlparsing.xml.constants.FilePathConstants.*;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final SupplierService supplierService;
    private final PartService partService;
    private final XmlParser xmlParser;

    @Autowired
    public CarServiceImpl(ModelMapper modelMapper, CarRepository carRepository,
                          @Lazy SupplierService supplierService, PartService partService, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.supplierService = supplierService;
        this.partService = partService;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedCars() throws JAXBException {
        CarSeedRootDto carsDtos = this.xmlParser.parseXml(CarSeedRootDto.class, CARS_FILE_PATH);
        for (CarDto car : carsDtos.getCars()) {
            if (this.carRepository.findByTravelledDistanceAndModel(car.getTravelledDistance(), car.getModel()) == null) {

                Set<Part> parts = this.partService.randomParts();
                Car map = this.modelMapper.map(car, Car.class);
                map.setParts(parts);

                this.carRepository.saveAndFlush(map);
            }


        }
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();
        long number = this.carRepository.count();
        int i = random.nextInt((int) number) + 1;
        Car one = this.carRepository.getOne((long) i);
        return one;
    }

    @Override
    public CarMakeViewRootDto getToyotaCars() {
        List<Car> toyota =
                this.carRepository.getAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        CarMakeViewRootDto carMakeViewRootDto = new CarMakeViewRootDto();

        List<CarMakeViewDto> collect = toyota.stream()
                .map(x -> this.modelMapper.map(x, CarMakeViewDto.class))
                .collect(Collectors.toList());

        carMakeViewRootDto.setCars(collect);
        return carMakeViewRootDto;
    }

    @Override
    public CarViewRootDto getCarsAndParts() {
        List<Car> all = this.carRepository.findAll();
        List<CarViewDto> carViewDtoList = new ArrayList<>();

        for (Car car : all) {

            CarViewDto map = this.modelMapper.map(car, CarViewDto.class);

            PartViewRootDto partViewRootDto = new PartViewRootDto();
            List<PartViewDto> partViewDtos = new ArrayList<>();

            for (Part part : car.getParts()) {
                PartViewDto map1 = this.modelMapper.map(part, PartViewDto.class);
                partViewDtos.add(map1);
            }
            partViewRootDto.setParts(partViewDtos);

            map.setParts(partViewRootDto);
            carViewDtoList.add(map);
        }
        CarViewRootDto carViewRootDto = new CarViewRootDto();
        carViewRootDto.setCars(carViewDtoList);

        return carViewRootDto;
    }
}
