package xmlparsing.xml.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import xmlparsing.xml.domain.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
