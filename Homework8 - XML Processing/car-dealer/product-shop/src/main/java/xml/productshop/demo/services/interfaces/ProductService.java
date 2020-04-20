package xml.productshop.demo.services.interfaces;


import org.springframework.stereotype.Service;
import xml.productshop.demo.domain.dtos.view.ProductsInRangeRootDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    void seedProducts() throws JAXBException;

    void setSomeSellersToNull();

    ProductsInRangeRootDto getProductsInRange() throws IOException;
}
