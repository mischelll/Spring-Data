package xmlparsing.xml.services;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.sales.CarSaleViewDto;
import xmlparsing.xml.domain.dtos.sales.SalesViewDto;
import xmlparsing.xml.domain.dtos.sales.SalesViewRootDto;
import xmlparsing.xml.domain.entities.Car;
import xmlparsing.xml.domain.entities.Customer;
import xmlparsing.xml.domain.entities.Sale;
import xmlparsing.xml.repositories.SaleRepository;
import xmlparsing.xml.services.interfaces.CarService;
import xmlparsing.xml.services.interfaces.CustomerService;
import xmlparsing.xml.services.interfaces.SaleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.math.RoundingMode.*;

@Service

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
            Car randomCar = this.carService.getRandomCar();
            Customer randomCustomer = this.customerService.getRandomCustomer();
            Double randomPercentage = discounts.get(randomIndex);

            Sale sale = new Sale();
            sale.setCar(randomCar);
            sale.setCustomer(randomCustomer);

            sale.setDiscountPercentage(randomPercentage);

            this.saleRepository.saveAndFlush(sale);

        }
    }

    @Override
    public SalesViewRootDto getSalesWithDiscount() {
        List<Sale> all = this.saleRepository.findAll();
        SalesViewRootDto salesViewRootDto = new SalesViewRootDto();

        List<SalesViewDto> list = new ArrayList<>();
        for (Sale sale : all) {
            SalesViewDto map = new SalesViewDto();
            SalesViewDto map1 = this.modelMapper.map(sale, SalesViewDto.class);


            double price = sale
                    .getCar()
                    .getParts()
                    .stream()
                    .mapToDouble(x -> x.getPrice().doubleValue())
                    .sum();
            map1.setPrice(BigDecimal.valueOf(price).setScale(2, HALF_UP));

            double priceWithDiscount = price - (price * sale.getDiscountPercentage());
            map1.setPriceWithDiscount(BigDecimal.valueOf(priceWithDiscount)
                    .setScale(2,HALF_UP));


            list.add(map1);
        }

        salesViewRootDto.setSales(list);
        return salesViewRootDto;
    }
}
