package xml.productshop.demo.services.impls;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xml.productshop.demo.domain.Product;
import xml.productshop.demo.domain.User;
import xml.productshop.demo.domain.dtos.binding.UserSeedRootDto;
import xml.productshop.demo.domain.dtos.view.ProductSoldViewDto;

import xml.productshop.demo.domain.dtos.view.UserSoldProductsDto;
import xml.productshop.demo.domain.dtos.view.UserSoldProductsRootDto;
import xml.productshop.demo.repositories.UserRepository;
import xml.productshop.demo.services.interfaces.UserService;
import xml.productshop.demo.utils.intefaces.ValidationUtil;
import xml.productshop.demo.utils.intefaces.XmlParser;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static xml.productshop.demo.constants.FIlePathConstants.USERS_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedUsers() throws JAXBException {
        UserSeedRootDto userSeedRootDto = this.xmlParser.parseXml(UserSeedRootDto.class, USERS_FILE_PATH);

        userSeedRootDto.getUsers().forEach(userSeedDto -> {
            if (validationUtil.isValid(userSeedDto)) {
                User user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
            } else {
                this.validationUtil
                        .getViolations(userSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });

    }

    @Override
    public User getRandomUser() {
        Random random = new Random();
        long number = this.userRepository.count();
        int i = random.nextInt((int) number) + 1;
        User one = this.userRepository.findById((long) i).orElse(null);

        return one;
    }

    @Override
    public UserSoldProductsRootDto getUserSoldProducts() {
        List<User> allBySoldSizeAndBuyerNotNull =
                this.userRepository.getAllBySoldSizeAndBuyerNotNull();

        UserSoldProductsRootDto userSoldProductsRootDto = new UserSoldProductsRootDto();
        List<UserSoldProductsDto> productsDtos = new ArrayList<>();

        for (User user : allBySoldSizeAndBuyerNotNull) {
            UserSoldProductsDto map = this.modelMapper.map(user, UserSoldProductsDto.class);
            List<ProductSoldViewDto> list = new ArrayList<>();

            for (Product product : user.getSold()) {
                ProductSoldViewDto map1 = this.modelMapper.map(product, ProductSoldViewDto.class);
                list.add(map1);
            }

            map.setSoldProducts(list);
            productsDtos.add(map);
        }
        userSoldProductsRootDto.setUsers(productsDtos);
        return userSoldProductsRootDto;
    }
}
