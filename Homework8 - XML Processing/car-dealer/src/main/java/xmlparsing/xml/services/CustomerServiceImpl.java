package xmlparsing.xml.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.customers.*;
import xmlparsing.xml.domain.entities.Customer;
import xmlparsing.xml.domain.entities.Sale;
import xmlparsing.xml.repositories.CustomerRepository;
import xmlparsing.xml.services.interfaces.CustomerService;
import xmlparsing.xml.utils.interfaces.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static xmlparsing.xml.constants.FilePathConstants.*;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;

    public CustomerServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedCustomers() throws JAXBException {
        CustomerSeedRootDto customerSeedRootDto = this.xmlParser
                .parseXml(CustomerSeedRootDto.class, CUSTOMERS_FILE_PATH);

        for (CustomerDto customer : customerSeedRootDto.getCustomers()) {
            if (this.customerRepository.findByName(customer.getName()) == null) {
                Customer map = this.modelMapper.map(customer, Customer.class);
                LocalDateTime localDateTime = LocalDateTime.parse(customer.getBirthDate());
                map.setDateOfBirth(localDateTime);
                this.customerRepository.saveAndFlush(map);
            }
        }
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();
        long number = this.customerRepository.count();
        int i = random.nextInt((int) number) + 1;
        Customer one = this.customerRepository.getOne((long) i);


        return one;
    }

    @Override
    public CustomerBirthDayViewRootDto getCustomersOrderedBirthday() {
        List<Customer> allByDateOfBirth = this.customerRepository.getAllByDateOfBirth();
        CustomerBirthDayViewRootDto customerBirthDayViewRootDto = new CustomerBirthDayViewRootDto();

        List<CustomerBirthDayViewDto> collect = allByDateOfBirth
                .stream()
                .map(x -> this.modelMapper.map(x, CustomerBirthDayViewDto.class))
                .collect(Collectors.toList());

        customerBirthDayViewRootDto.setCustomers(collect);

        return customerBirthDayViewRootDto;
    }

    @Override
    public CustomerSalesRootDto getCustomersPurchasesCount() {
        List<Customer> all = this.customerRepository.findAll();
        CustomerSalesRootDto customerSalesRootDto = new CustomerSalesRootDto();

        List<CustomerSalesDto> customerSalesDtos = new LinkedList<>();
        for (Customer customer : all) {
            CustomerSalesDto customerSalesDto = new CustomerSalesDto();

            customerSalesDto.setBoughtCars(customer.getPurchases().size());
            customerSalesDto.setFullName(customer.getName());

            for (Sale purchase : customer.getPurchases()) {
                double sum = purchase
                        .getCar()
                        .getParts()
                        .stream()
                        .mapToDouble(x -> x.getPrice().doubleValue())
                        .sum();

                customerSalesDto.setSpentMoney(
                        BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP));

            }
            customerSalesDtos.add(customerSalesDto);
        }
        customerSalesDtos.removeIf(x-> x.getBoughtCars() == 0);
        List<CustomerSalesDto> collect = customerSalesDtos.stream()
                .sorted((a, b) -> {
                    int sort = 0;
                    if (a.getBoughtCars() != 0 && b.getBoughtCars() != 0) {

                        sort = b.getSpentMoney().compareTo(a.getSpentMoney());
                        if (sort == 0) {
                            sort = b.getBoughtCars() - a.getBoughtCars();
                        }
                    }
                    return sort;
                }).collect(Collectors.toList());


        customerSalesRootDto.setCustomers(collect);
        return customerSalesRootDto;
    }


}
