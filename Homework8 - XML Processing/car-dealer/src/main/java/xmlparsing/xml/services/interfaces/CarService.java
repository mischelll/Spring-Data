package xmlparsing.xml.services.interfaces;


import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.cars.CarMakeViewRootDto;
import xmlparsing.xml.domain.dtos.cars.CarViewRootDto;
import xmlparsing.xml.domain.entities.Car;

import javax.xml.bind.JAXBException;

@Service
public interface CarService {
    void seedCars() throws JAXBException;

    Car getRandomCar();

   CarMakeViewRootDto getToyotaCars();

   CarViewRootDto getCarsAndParts();
}
