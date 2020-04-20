package xml.productshop.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xml.productshop.demo.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    Product findByNameAndPrice(String name, BigDecimal price);

    List<Product> getAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal lower, BigDecimal higher);
}