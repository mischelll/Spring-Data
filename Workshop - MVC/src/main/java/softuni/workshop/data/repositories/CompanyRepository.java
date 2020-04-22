package softuni.workshop.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.Company;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findByName(String name);
}
