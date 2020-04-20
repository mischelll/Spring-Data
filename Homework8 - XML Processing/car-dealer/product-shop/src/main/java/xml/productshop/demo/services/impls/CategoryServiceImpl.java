package xml.productshop.demo.services.impls;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xml.productshop.demo.domain.Category;
import xml.productshop.demo.domain.dtos.binding.CategorySeedRootDto;
import xml.productshop.demo.domain.dtos.view.CategoryProductsCountDto;
import xml.productshop.demo.domain.dtos.view.CategoryProductsCountRootDto;
import xml.productshop.demo.repositories.CategoryRepository;
import xml.productshop.demo.services.interfaces.CategoryService;
import xml.productshop.demo.utils.intefaces.ValidationUtil;
import xml.productshop.demo.utils.intefaces.XmlParser;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;

import java.util.*;

import static xml.productshop.demo.constants.FIlePathConstants.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedCategories() throws JAXBException {
        CategorySeedRootDto categorySeedRootDto = this.xmlParser.parseXml(CategorySeedRootDto.class, CATEGORIES_FILE_PATH);
        categorySeedRootDto.getCategories().stream().filter(categorySeedDto -> {
            if (categoryRepository.findByName(categorySeedDto.getName()) == null) {
                return true;
            }
            return false;
        }).forEach(categorySeedDto -> {
            if (this.validationUtil.isValid(categorySeedDto)) {
                Category map = this.modelMapper.map(categorySeedDto, Category.class);

                this.categoryRepository.saveAndFlush(map);
            } else {
                this.validationUtil
                        .getViolations(categorySeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });


    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();
        int randomCounter = random.nextInt(3) + 1;
        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < randomCounter; i++) {
            long randomId = random.nextInt((int) this.categoryRepository.count()) + 1;
            categories.add(this.categoryRepository.getOne(randomId));
        }

        return categories;
    }

    @Override
    public CategoryProductsCountRootDto getCategoriesProductsCount() {
        List<CategoryProductsCountDto> categoriesByNameSizeAvgPrice =
                this.categoryRepository.getCategoriesByNameSizeAvgPrice();

        CategoryProductsCountRootDto categoryProductsCountRootDto = new CategoryProductsCountRootDto();
       categoryProductsCountRootDto.setCategories(categoriesByNameSizeAvgPrice);

        return categoryProductsCountRootDto;

    }
}

