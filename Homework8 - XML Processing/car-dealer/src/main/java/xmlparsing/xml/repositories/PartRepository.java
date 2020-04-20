package xmlparsing.xml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xmlparsing.xml.domain.entities.Part;

import java.math.BigDecimal;

public interface PartRepository extends JpaRepository<Part, Long> {
    Part findByNameAndPrice(String name, BigDecimal price);
    Part findByPrice(BigDecimal price);
}
