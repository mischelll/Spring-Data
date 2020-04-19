package com.cardealer.cardealer.services.interfaces;

import com.cardealer.cardealer.domain.dtos.CarMakeDto;
import com.cardealer.cardealer.domain.dtos.CarPartsDto;
import com.cardealer.cardealer.domain.dtos.CarSeedDto;
import com.cardealer.cardealer.domain.entities.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    void seedCars(CarSeedDto[] carSeedDtos);

    Car getRandomCar();

    List<CarMakeDto> getCarsByMakeToyota();

    List<CarPartsDto> getAllCarParts();
}
