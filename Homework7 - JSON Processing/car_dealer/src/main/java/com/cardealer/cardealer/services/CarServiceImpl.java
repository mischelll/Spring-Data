package com.cardealer.cardealer.services;

import com.cardealer.cardealer.domain.dtos.*;
import com.cardealer.cardealer.domain.entities.Car;
import com.cardealer.cardealer.domain.entities.Part;
import com.cardealer.cardealer.repositories.CarRepository;
import com.cardealer.cardealer.services.interfaces.CarService;
import com.cardealer.cardealer.services.interfaces.PartService;
import com.cardealer.cardealer.services.interfaces.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class CarServiceImpl implements CarService {
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final SupplierService supplierService;
    private final PartService partService;

    public CarServiceImpl(ModelMapper modelMapper, CarRepository carRepository,
                          @Lazy SupplierService supplierService, PartService partService) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.supplierService = supplierService;
        this.partService = partService;
    }

    @Override
    public void seedCars(CarSeedDto[] carSeedDtos) {
        Arrays.stream(carSeedDtos).forEach(carSeedDto -> {
            Car car = this.modelMapper.map(carSeedDto, Car.class);
            car.setParts(new HashSet<>(getRandomParts()));

            this.carRepository.saveAndFlush(car);
        });
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
    public List<CarMakeDto> getCarsByMakeToyota() {
        List<Car> toyota =
                this.carRepository.getAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        List<CarMakeDto> carMakeDtos = new ArrayList<>();
        toyota.forEach(car -> {
            CarMakeDto makeDto = this.modelMapper.map(car, CarMakeDto.class);
            carMakeDtos.add(makeDto);
        });
        System.out.println();
        return carMakeDtos;
    }

    @Override
    public List<CarPartsDto> getAllCarParts() {
        List<Car> all = this.carRepository.findAll();
        List<CarPartsDto> carPartsDtos = new ArrayList<>();

        for (Car car : all) {
            CarPartsDto carPartsDto = new CarPartsDto();
            CarViewDto carViewDto = this.modelMapper.map(car, CarViewDto.class);

            Set<PartViewDto> dtoSet = new HashSet<>();
            for (Part part : car.getParts()) {
                PartViewDto partViewDto = this.modelMapper.map(part, PartViewDto.class);
                dtoSet.add(partViewDto);
            }

            carPartsDto.setCar(carViewDto);
            carPartsDto.setPartSet(dtoSet);

            carPartsDtos.add(carPartsDto);
        }
        return carPartsDtos;
    }

    private List<Part> getRandomParts() {
        return this.partService.generateRandomParts();
    }
}
