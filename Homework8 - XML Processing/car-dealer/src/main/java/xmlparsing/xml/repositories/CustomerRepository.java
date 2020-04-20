package xmlparsing.xml.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xmlparsing.xml.domain.dtos.customers.CustomerSalesDto;
import xmlparsing.xml.domain.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);

    @Query("SELECT c FROM Customer AS c ORDER BY c.dateOfBirth ASC , c.youngDriver")
    List<Customer> getAllByDateOfBirth();

//    @Query("select new xmlparsing.xml.domain.dtos.customers.CustomerSalesDto(c.name, size(c.purchases)) " +
//            "from Customer as c")
//    List<CustomerSalesDto> getCustomersCars();
}
