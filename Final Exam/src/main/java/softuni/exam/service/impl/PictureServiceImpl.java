package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PictureSeedDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.GlobalConstants.*;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final CarRepository carRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, CarRepository carRepository) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.carRepository = carRepository;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        String json = "";
        try {
            json = String.join("\n", Files.readAllLines(Paths.get(PICTURES_PATH_JSON)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return json;
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder result = new StringBuilder();
        PictureSeedDto[] pictureSeedDtos = this.gson.fromJson(new FileReader(PICTURES_PATH_JSON), PictureSeedDto[].class);
        System.out.println();
        Arrays.stream(pictureSeedDtos).forEach(pictureSeedDto -> {
            if (this.validationUtil.isValid(pictureSeedDto)){
                if (this.pictureRepository.findByName(pictureSeedDto.getName()) == null){
                    LocalDateTime dateTime = LocalDateTime.parse(pictureSeedDto.getDateAndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    Car car = this.carRepository.findById(pictureSeedDto.getCar()).orElse(null);
                    Picture picture = this.modelMapper.map(pictureSeedDto, Picture.class);
                    picture.setCar(car);
                    picture.setDateAndTime(dateTime);

                    this.pictureRepository.saveAndFlush(picture);
                    result.append(String.format(SUCCESSFULL_IMPORT_PICTURE,picture.getName())).append(System.lineSeparator());
                }
            }else {
                result.append(String.format(UNSUCCESSFULL_IMPORT,
                        Picture.class
                                .getSimpleName()
                                .toLowerCase()))
                        .append(System.lineSeparator());
            }
        });
        return result.toString();
    }
}
