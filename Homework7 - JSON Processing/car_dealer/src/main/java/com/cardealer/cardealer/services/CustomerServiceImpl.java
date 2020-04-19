package com.cardealer.cardealer.services;

import com.cardealer.cardealer.domain.dtos.*;
import com.cardealer.cardealer.domain.entities.Customer;
import com.cardealer.cardealer.domain.entities.Part;
import com.cardealer.cardealer.domain.entities.Sale;
import com.cardealer.cardealer.repositories.CustomerRepository;
import com.cardealer.cardealer.services.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service

public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedCustomers(CustomerSeedDto[] customerSeedDtos) {
        Arrays.stream(customerSeedDtos).forEach(customerSeedDto -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(customerSeedDto.getBirthDate(), formatter);

            Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
            customer.setDateOfBirth(dateTime);

            this.customerRepository.saveAndFlush(customer);
            System.out.println();
        });
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
    public List<CustomerBirthdateDto> getBirthdateOrderedCustomers() {
        List<Customer> allByDateOfBirthOrderByDateOfBirthAscIsYoungDriverDesc =
                this.customerRepository.getAllByDateOfBirth();

        List<CustomerBirthdateDto> birthdateDtos = new ArrayList<>();
        for (Customer customer : allByDateOfBirthOrderByDateOfBirthAscIsYoungDriverDesc) {
            CustomerBirthdateDto customerBirthdateDto = this.modelMapper.map(customer, CustomerBirthdateDto.class);
            customerBirthdateDto.setBirthDate(customer.getDateOfBirth().toString());

            Set<SaleViewDto> saleViewDtoSet = new HashSet<>();
            for (Sale purchase : customer.getPurchases()) {
                SaleViewDto map1 = this.modelMapper.map(purchase, SaleViewDto.class);
                map1.setCar(this.modelMapper.map(purchase.getCar(), CarViewDto.class));
                saleViewDtoSet.add(map1);
            }
            customerBirthdateDto.setPurchases(saleViewDtoSet);
            birthdateDtos.add(customerBirthdateDto);
        }


        return birthdateDtos;
    }

    @Override
    public List<CustomerSalesDto> getCustomerCarsCount() {
        List<Customer> all = this.customerRepository.findAll();
        List<CustomerSalesDto> customerSalesDtos = new ArrayList<>();

        for (Customer customer : all) {
            CustomerSalesDto customerSalesDto = new CustomerSalesDto();
            customerSalesDto.setFullName(customer.getName());
            customerSalesDto.setBoughtCars(customer.getPurchases().size());
            for (Sale purchase : customer.getPurchases()) {
                double sum = purchase
                        .getCar()
                        .getParts()
                        .stream()
                        .mapToDouble(x -> x.getPrice().doubleValue())
                        .sum();
                double decrease  = sum * purchase.getDiscountPercentage();
                sum -= decrease;

                customerSalesDto.setSpentMoney(new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP));
            }

            customerSalesDtos.add(customerSalesDto);
        }

        return customerSalesDtos;
    }
}
