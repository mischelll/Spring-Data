package xml.productshop.demo.runners;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xml.productshop.demo.constants.FIlePathConstants;
import xml.productshop.demo.domain.dtos.view.CategoryProductsCountRootDto;
import xml.productshop.demo.domain.dtos.view.ProductsInRangeRootDto;
import xml.productshop.demo.domain.dtos.view.UserSoldProductsRootDto;
import xml.productshop.demo.services.interfaces.CategoryService;
import xml.productshop.demo.services.interfaces.ProductService;
import xml.productshop.demo.services.interfaces.UserService;
import xml.productshop.demo.utils.intefaces.FileIO;
import xml.productshop.demo.utils.intefaces.XmlParser;

import java.io.BufferedReader;

import static xml.productshop.demo.constants.FIlePathConstants.*;

@Component
public class AppRunner implements CommandLineRunner {
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FileIO fileIO;
    private final BufferedReader bufferedReader;

    public AppRunner(ModelMapper modelMapper, XmlParser xmlParser,
                     CategoryService categoryService, UserService userService,
                     ProductService productService, FileIO fileIO, BufferedReader bufferedReader) {

        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.fileIO = fileIO;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        * 1. Seed the database
        * 2. Execute the queries(I haven't made the 4th one.)
        * 3. Check the output in resources\output-files
        *
        * p.s. There is no UI here because it is fairly simple to navigate throughout the code. :)
        * */
//        this.userService.seedUsers();
//        this.categoryService.seedCategories();
//        this.productService.seedProducts();
//        this.productService.setSomeSellersToNull();

//        ProductsInRangeRootDto productsInRange = this.productService.getProductsInRange();
//        this.xmlParser.exportToXml(productsInRange,
//                ProductsInRangeRootDto.class, QUERY_ONE_FILE_PATH);

//        UserSoldProductsRootDto userSoldProducts = this.userService.getUserSoldProducts();
//        this.xmlParser.exportToXml(userSoldProducts,UserSoldProductsRootDto.class,QUERY_TWO_FILE_PATH);

//        CategoryProductsCountRootDto categoriesProductsCount = this.categoryService.getCategoriesProductsCount();
//        this.xmlParser.exportToXml(categoriesProductsCount,
//                CategoryProductsCountRootDto.class,QUERY_THREE_FILE_PATH);
    }
}
