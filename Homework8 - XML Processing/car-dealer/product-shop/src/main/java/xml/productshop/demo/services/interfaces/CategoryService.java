package xml.productshop.demo.services.interfaces;


import org.springframework.stereotype.Service;
import xml.productshop.demo.domain.Category;
import xml.productshop.demo.domain.dtos.view.CategoryProductsCountRootDto;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Set;

@Service
public interface CategoryService {
    void seedCategories() throws JAXBException;

    Set<Category> getRandomCategories();

    CategoryProductsCountRootDto getCategoriesProductsCount();
}
