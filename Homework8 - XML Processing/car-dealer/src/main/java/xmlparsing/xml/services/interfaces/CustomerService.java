package xmlparsing.xml.services.interfaces;


import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.customers.CustomerBirthDayViewRootDto;
import xmlparsing.xml.domain.dtos.customers.CustomerSalesRootDto;
import xmlparsing.xml.domain.entities.Customer;

import javax.xml.bind.JAXBException;

@Service
public interface CustomerService {
    void seedCustomers() throws JAXBException;

    Customer getRandomCustomer();

    CustomerBirthDayViewRootDto getCustomersOrderedBirthday();

    CustomerSalesRootDto getCustomersPurchasesCount();


}
