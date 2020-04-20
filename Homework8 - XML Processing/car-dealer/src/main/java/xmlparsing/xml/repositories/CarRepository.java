package xmlparsing.xml.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xmlparsing.xml.domain.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    List<Car> getAllByMakeOrderByModelAscTravelledDistanceDesc(String make);

    List<Car> findAll();

    Car findByTravelledDistanceAndModel(Long travelledDistance, String model);
    Car findByModel(String model);
}
