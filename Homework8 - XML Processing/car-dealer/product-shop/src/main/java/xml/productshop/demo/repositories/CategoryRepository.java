package xml.productshop.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xml.productshop.demo.domain.Category;
import xml.productshop.demo.domain.dtos.view.CategoryProductsCountDto;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("select new xml.productshop.demo.domain.dtos.view." +
            "CategoryProductsCountDto(c.name , size(c.products), avg (p.price),sum(p.price))" +
            "from Category as c " +
            "join c.products as p " +
            "group by c.id " +
            "order by size(c.products) desc ")
    List<CategoryProductsCountDto> getCategoriesByNameSizeAvgPrice();
}