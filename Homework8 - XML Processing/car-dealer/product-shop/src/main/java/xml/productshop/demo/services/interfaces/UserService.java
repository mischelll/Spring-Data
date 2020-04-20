package xml.productshop.demo.services.interfaces;


import org.springframework.stereotype.Service;
import xml.productshop.demo.domain.User;
import xml.productshop.demo.domain.dtos.view.UserSoldProductsRootDto;

import javax.xml.bind.JAXBException;

@Service
public interface UserService {
    void seedUsers() throws JAXBException;

    User getRandomUser();

    UserSoldProductsRootDto getUserSoldProducts();
}
