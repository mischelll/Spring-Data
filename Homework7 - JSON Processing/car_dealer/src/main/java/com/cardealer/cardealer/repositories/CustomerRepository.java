package com.cardealer.cardealer.repositories;

import com.cardealer.cardealer.domain.dtos.CustomerSalesDto;
import com.cardealer.cardealer.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer AS c ORDER BY c.dateOfBirth ASC , c.youngDriver")
    List<Customer> getAllByDateOfBirth();

    @Query("select new com.cardealer.cardealer.domain.dtos.CustomerSalesDto(c.name, size(c.purchases)) " +
            "from Customer as c")
    List<CustomerSalesDto> getCustomersCars();
}
