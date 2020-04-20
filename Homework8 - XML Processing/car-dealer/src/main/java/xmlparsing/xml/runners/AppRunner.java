package xmlparsing.xml.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xmlparsing.xml.constants.FilePathConstants;
import xmlparsing.xml.domain.dtos.cars.CarMakeViewRootDto;
import xmlparsing.xml.domain.dtos.cars.CarViewRootDto;
import xmlparsing.xml.domain.dtos.customers.CustomerBirthDayViewRootDto;
import xmlparsing.xml.domain.dtos.customers.CustomerSalesRootDto;
import xmlparsing.xml.domain.dtos.sales.SalesViewRootDto;
import xmlparsing.xml.domain.dtos.suppliers.SupplierLocalViewRootDto;
import xmlparsing.xml.services.interfaces.*;
import xmlparsing.xml.utils.interfaces.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;

import static xmlparsing.xml.constants.FilePathConstants.*;

@Component
public class AppRunner implements CommandLineRunner {
    private final CarService carService;
    private final CustomerService customerService;
    private final PartService partService;
    private final SaleService saleService;
    private final SupplierService supplierService;
    private final XmlParser xmlParser;
    private final BufferedReader bufferedReader;

    @Autowired
    public AppRunner(CarService carService, CustomerService customerService,
                     PartService partService, SaleService saleService, SupplierService supplierService, XmlParser xmlParser, BufferedReader bufferedReader) {
        this.carService = carService;
        this.customerService = customerService;
        this.partService = partService;
        this.saleService = saleService;
        this.supplierService = supplierService;
        this.xmlParser = xmlParser;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        1.Seed the database
        2. Execute the queries
        3. Check the output in resources\output
        */
        try {
            while (true) {
                System.out.println("Enter command:");
                String command = this.bufferedReader.readLine();
                switch (command) {
                    case "seed":
                        this.supplierService.seedSuppliers();
                        this.partService.seedParts();
                        this.carService.seedCars();
                        this.customerService.seedCustomers();
                        this.saleService.seedSales();
                        break;
                    case "execute":
                        this.executeQueryOne();
                        this.executeQueryTwo();
                        this.executeQueryThree();
                        this.executeQueryFour();
                        this.executeQueryFive();
                        this.executeQuerySix();
                        break;
                    case "end":
                        return;
                    default:
                        System.out.println("Wrong command!");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void executeQuerySix() throws JAXBException {
        SalesViewRootDto salesWithDiscount = this.saleService.getSalesWithDiscount();
        this.xmlParser.exportToXml(salesWithDiscount,
                SalesViewRootDto.class, QUERY_SIX_FILE_PATH);
    }

    private void executeQueryFive() throws JAXBException {
        CustomerSalesRootDto customersPurchasesCount = this.customerService.getCustomersPurchasesCount();
        this.xmlParser.exportToXml(customersPurchasesCount
                , CustomerSalesRootDto.class, QUERY_FIVE_FILE_PATH);
    }

    private void executeQueryFour() throws JAXBException {
        CarViewRootDto customersCars =
                this.carService.getCarsAndParts();
        this.xmlParser.exportToXml(customersCars,
                CarViewRootDto.class, QUERY_FOUR_FILE_PATH);
    }

    private void executeQueryThree() throws JAXBException {
        SupplierLocalViewRootDto localViewRootDto = this.supplierService.getLocalSuppliers();
        this.xmlParser.exportToXml(localViewRootDto,
                SupplierLocalViewRootDto.class, QUERY_THREE_FILE_PATH);
    }

    private void executeQueryTwo() throws JAXBException {
        CarMakeViewRootDto toyotaCars = this.carService.getToyotaCars();
        this.xmlParser.exportToXml(toyotaCars,
                CarMakeViewRootDto.class, QUERY_TWO_FILE_PATH);
    }


    private void executeQueryOne() throws JAXBException {
        CustomerBirthDayViewRootDto customersOrderedBirthday =
                this.customerService.getCustomersOrderedBirthday();
        this.xmlParser.exportToXml(customersOrderedBirthday,
                CustomerBirthDayViewRootDto.class, QUERY_ONE_FILE_PATH);
    }
}
