package com.cardealer.cardealer.services;

import com.cardealer.cardealer.domain.dtos.CarViewDto;
import com.cardealer.cardealer.domain.dtos.SalesDiscountDto;
import com.cardealer.cardealer.domain.entities.Car;
import com.cardealer.cardealer.domain.entities.Customer;
import com.cardealer.cardealer.domain.entities.Sale;
import com.cardealer.cardealer.repositories.SaleRepository;
import com.cardealer.cardealer.services.interfaces.CarService;
import com.cardealer.cardealer.services.interfaces.CustomerService;
import com.cardealer.cardealer.services.interfaces.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {
    private final ModelMapper modelMapper;
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;

    public SaleServiceImpl(ModelMapper modelMapper, SaleRepository saleRepository, CarService carService, CustomerService customerService) {
        this.modelMapper = modelMapper;
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override

    public void seedSales() {
        List<Double> discounts = new ArrayList<Double>();
        discounts.add(0.05);
        discounts.add(0.1);
        discounts.add(0.15);
        discounts.add(0.2);
        discounts.add(0.3);
        discounts.add(0.4);
        discounts.add(0.5);

        Random random = new Random();

        for (int i = 0; i < 30; i++) {
            int randomIndex = random.nextInt(discounts.size());
            Customer randomCustomer = this.customerService.getRandomCustomer();
            Car randomCar = this.carService.getRandomCar();
            Double randomPercentage = discounts.get(randomIndex);

            Sale sale = new Sale();
            sale.setCar(randomCar);
            sale.setCustomer(randomCustomer);
            sale.setDiscountPercentage(randomPercentage);

            this.saleRepository.saveAndFlush(sale);

        }

    }

    @Override
    public List<SalesDiscountDto> getAllSalesDiscounts() {
        List<Sale> all = this.saleRepository.findAll();
        List<SalesDiscountDto> salesDiscountDtos = new ArrayList<>();

        for (Sale sale : all) {
            SalesDiscountDto salesDiscountDto = new SalesDiscountDto();

            salesDiscountDto.setCar(this.modelMapper.map(sale.getCar(), CarViewDto.class));
            salesDiscountDto.setCustomerName(sale.getCustomer().getName());
            salesDiscountDto.setDiscount(sale.getDiscountPercentage());

           salesDiscountDto.setPrice(BigDecimal.valueOf(getCarPrice(sale))
                   .setScale(2, RoundingMode.HALF_UP));
           double priceWithDiscount  = getCarPrice(sale) - (getCarPrice(sale)* sale.getDiscountPercentage());

           salesDiscountDto.setPriceWithDiscount(BigDecimal.valueOf(priceWithDiscount)
                   .setScale(2, RoundingMode.HALF_UP));

           salesDiscountDtos.add(salesDiscountDto);
        }

        return salesDiscountDtos;
    }

    private double getCarPrice(Sale sale){
       return sale
                .getCar()
                .getParts()
                .stream()
                .mapToDouble(x -> x.getPrice().doubleValue())
                .sum();
    }
}
