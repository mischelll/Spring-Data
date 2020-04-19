package com.cardealer.cardealer.services.interfaces;

import com.cardealer.cardealer.domain.dtos.CustomerBirthdateDto;
import com.cardealer.cardealer.domain.dtos.CustomerSalesDto;
import com.cardealer.cardealer.domain.dtos.CustomerSeedDto;
import com.cardealer.cardealer.domain.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    void seedCustomers(CustomerSeedDto[] customerSeedDtos);

    Customer getRandomCustomer();

    List<CustomerBirthdateDto> getBirthdateOrderedCustomers();

    List<CustomerSalesDto> getCustomerCarsCount();
}
