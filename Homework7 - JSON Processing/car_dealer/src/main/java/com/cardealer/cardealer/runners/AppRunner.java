package com.cardealer.cardealer.runners;

import com.cardealer.cardealer.domain.dtos.*;
import com.cardealer.cardealer.services.interfaces.*;
import com.cardealer.cardealer.utils.interfaces.FileIO;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.cardealer.cardealer.constants.Constants.*;

@Component

public class AppRunner implements CommandLineRunner {
    private final Gson gson;
    private final BufferedReader bufferedReader;
    private final SupplierService supplierService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final PartService partService;
    private final FileIO fileIO;

    public AppRunner(Gson gson, BufferedReader bufferedReader, SupplierService supplierService
            , CarService carService, CustomerService customerService, SaleService saleService, PartService partService, FileIO fileIO) {
        this.gson = gson;
        this.bufferedReader = bufferedReader;
        this.supplierService = supplierService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.partService = partService;
        this.fileIO = fileIO;
    }

    @Override
    public void run(String... args) throws Exception {
        //-> SEED THE DATABASE
        //-> EXECUTE QUERIES(NUMBERED FROM 1 TO 6 ACCORDING TO THE EXERCISE FILE)
        // !!! DELETE THE CONTENT FROM THE query1, query2... files FIRST!!!!
        //-> CHECK THE RESULTS IN THE RESOURCE FOLDER-> OUTPUTS DIRECTORY-> query1.txt, query2.txt...and so on..
        //-> ENTER end TO EXIT THE PROGRAM
        try {
            while (true) {
                System.out.println("Enter command:");
                String command = this.bufferedReader.readLine().toLowerCase();
                switch (command) {
                    case "seed database":
                        seedSuppliers();
                        seedParts();
                        seedCars();
                        seedCustomers();
                        seedSales();
                        break;
                    case "1":
                        this.writeOrderedCustomers();
                        break;
                    case "2":
                        this.writeOrderedCars();
                        break;
                    case "3":
                        this.writeLocalSuppliers();
                        break;
                    case "4":
                        this.writeCarParts();
                        break;
                    case "5":
                        this.writeCustomerPuchases();
                        break;
                    case "6":
                        this.writeSaleDiscounts();
                        break;
                    case "end":
                        return;

                    default:
                        System.out.println("Invalid input!");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void writeSaleDiscounts() throws IOException {
        List<SalesDiscountDto> allSalesDiscounts = this.saleService.getAllSalesDiscounts();
        String toJson = this.gson.toJson(allSalesDiscounts);
        this.fileIO.write(toJson, QUERY_SIX_FILE_PATH);
    }

    private void writeCustomerPuchases() throws IOException {
        List<CustomerSalesDto> customerCarsCount = this.customerService.getCustomerCarsCount();
        String toJson = this.gson.toJson(customerCarsCount);
        this.fileIO.write(toJson, QUERY_FIVE_FILE_PATH);
    }

    private void writeCarParts() throws IOException {
        List<CarPartsDto> allCarParts = this.carService.getAllCarParts();
        String toJson = this.gson.toJson(allCarParts);
        this.fileIO.write(toJson, QUERY_FOUR_FILE_PATH);
    }

    private void writeLocalSuppliers() throws IOException {
        List<LocalSupplierDto> supplierDtoList = this.supplierService.localSuppliers();
        String toJson = this.gson.toJson(supplierDtoList);
        this.fileIO.write(toJson, QUERY_THREE_FILE_PATH);
    }

    private void writeOrderedCars() throws IOException {
        List<CarMakeDto> carsByMakeToyota = this.carService.getCarsByMakeToyota();
        String toJson = this.gson.toJson(carsByMakeToyota);
        this.fileIO.write(toJson, QUERY_TWO_FILE_PATH);

    }

    private void writeOrderedCustomers() throws IOException {
        List<CustomerBirthdateDto> birthdateOrderedCustomers = this.customerService.getBirthdateOrderedCustomers();

        String toJson = this.gson.toJson(birthdateOrderedCustomers);
        this.fileIO.write(toJson, QUERY_ONE_FILE_PATH);
    }


    private void seedSales() {
        this.saleService.seedSales();
    }

    private void seedSuppliers() throws FileNotFoundException {
        SupplierSeedDto[] supplierSeedDtos =
                this.gson.fromJson(new FileReader(SUPPLIERS_FILE_PATH), SupplierSeedDto[].class);

        this.supplierService.seedSuppliers(supplierSeedDtos);
    }

    private void seedCustomers() throws FileNotFoundException {
        CustomerSeedDto[] customerSeedDtos =
                this.gson.fromJson(new FileReader(CUSTOMERS_FILE_PATH), CustomerSeedDto[].class);

        this.customerService.seedCustomers(customerSeedDtos);
    }


    private void seedParts() throws FileNotFoundException {
        PartsSeedDto[] partsSeedDtos =
                this.gson.fromJson(new FileReader(PARTS_FILE_PATH), PartsSeedDto[].class);

        this.partService.seedParts(partsSeedDtos);
    }

    private void seedCars() throws FileNotFoundException {
        CarSeedDto[] carSeedDtos =
                this.gson.fromJson(new FileReader(CARS_FILE_PATH), CarSeedDto[].class);

        this.carService.seedCars(carSeedDtos);
    }
}
