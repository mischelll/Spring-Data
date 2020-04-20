package xml.productshop.demo.services.impls;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import xml.productshop.demo.domain.Category;
import xml.productshop.demo.domain.Product;
import xml.productshop.demo.domain.User;
import xml.productshop.demo.domain.dtos.binding.ProductSeedDto;
import xml.productshop.demo.domain.dtos.binding.ProductSeedRootDto;
import xml.productshop.demo.domain.dtos.view.ProductsInRangeDto;
import xml.productshop.demo.domain.dtos.view.ProductsInRangeRootDto;
import xml.productshop.demo.repositories.ProductRepository;
import xml.productshop.demo.services.interfaces.CategoryService;
import xml.productshop.demo.services.interfaces.ProductService;
import xml.productshop.demo.services.interfaces.UserService;
import xml.productshop.demo.utils.intefaces.ValidationUtil;
import xml.productshop.demo.utils.intefaces.XmlParser;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static xml.productshop.demo.constants.FIlePathConstants.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final BufferedReader bufferedReader;
    private final XmlParser xmlParser;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil,
                              @Lazy UserService userService,
                              CategoryService categoryService, BufferedReader bufferedReader, XmlParser xmlParser) {

        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.bufferedReader = bufferedReader;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedProducts() throws JAXBException {
        ProductSeedRootDto productSeedRootDto = this.xmlParser.parseXml(ProductSeedRootDto.class, PRODUCTS_FILE_PATH);

        productSeedRootDto.getProducts().stream().filter(productSeedDto -> {
            if (this.productRepository.findByNameAndPrice(productSeedDto.getName(), productSeedDto.getPrice()) == null) {
                return true;
            }
            return false;
        }).forEach(productSeedDto -> {
            if (this.validationUtil.isValid(productSeedDto)) {
                Product map = this.modelMapper.map(productSeedDto, Product.class);
                User buyer = this.userService.getRandomUser();
                User seller = this.userService.getRandomUser();
                Set<Category> randomCategories = this.categoryService.getRandomCategories();

                map.setBuyer(buyer);
                map.setSeller(seller);
                map.setCategories(randomCategories);

                this.productRepository.saveAndFlush(map);
            } else {
                this.validationUtil
                        .getViolations(productSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });
    }

    @Override
    public void setSomeSellersToNull() {
        Random random = new Random();
        long number = this.productRepository.count();


        for (int j = 0; j < 15; j++) {
            int i = random.nextInt((int) number) + 1;
            Product one = this.productRepository.getOne((long) i);
            one.setBuyer(null);

        }
    }

    @Override
    public ProductsInRangeRootDto getProductsInRange() throws IOException {
        System.out.println("Enter price range[lower-higher]");
        System.out.println("Enter lower:");
        BigDecimal lower = BigDecimal.valueOf(Double.parseDouble(this.bufferedReader.readLine()));
        System.out.println("Enter higher:");
        BigDecimal higher = BigDecimal.valueOf(Double.parseDouble(this.bufferedReader.readLine()));

        List<Product> allByPriceBetweenAndAndBuyerIsNull = this.productRepository
                .getAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(lower, higher);

        ProductsInRangeRootDto productsInRangeRootDto = new ProductsInRangeRootDto();
        List<ProductsInRangeDto> products = new ArrayList<>();

        for (Product product : allByPriceBetweenAndAndBuyerIsNull) {
            ProductsInRangeDto map = this.modelMapper.map(product, ProductsInRangeDto.class);
            if (product.getSeller().getFirstName() == null) {
                map.setSeller(String.format("%s",
                        product.getSeller().getLastName()));
            } else {

                map.setSeller(String.format("%s %s", product.getSeller().getFirstName(),
                        product.getSeller().getLastName()));
            }

            products.add(map);
        }

        productsInRangeRootDto.setProducts(products);

        return productsInRangeRootDto;
    }
}
