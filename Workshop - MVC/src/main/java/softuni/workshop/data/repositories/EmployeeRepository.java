package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
 List<Employee> findAllByAgeGreaterThan(Integer age);
}
