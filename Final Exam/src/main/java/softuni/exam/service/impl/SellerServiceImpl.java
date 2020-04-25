package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.SellerSeedDto;
import softuni.exam.models.dtos.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.models.entities.SellerRating;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static softuni.exam.constants.GlobalConstants.*;
import static softuni.exam.constants.GlobalConstants.CARS_PATH_JSON;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        String xml = "";

        try {
            xml = String.join("\n", Files.readAllLines(Paths.get(SELLERS_PATH_XML)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return xml;
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();
        SellerSeedRootDto seedRootDto = this.xmlParser.parseXml(SellerSeedRootDto.class, SELLERS_PATH_XML);
        System.out.println();
        for (SellerSeedDto sellerSeedDto : seedRootDto.getSellerSeedDtoList()) {
            if (this.validationUtil.isValid(sellerSeedDto) && (sellerSeedDto.getRating().equals("GOOD")
                    ||sellerSeedDto.getRating().equals("BAD")
                    ||sellerSeedDto.getRating().equals("UNKNOWN"))) {
                if (this.sellerRepository.findByEmail(sellerSeedDto.getEmail()) == null) {
                    Seller seller = this.modelMapper.map(sellerSeedDto, Seller.class);
                    System.out.println();

                    this.sellerRepository.saveAndFlush(seller);
                    stringBuilder.append(String.format(SUCCESSFULL_IMPORT_SELLER, seller.getLastName(), seller.getEmail())).append(System.lineSeparator());

                    System.out.println();
                }
            } else {
                stringBuilder.append(String.format(UNSUCCESSFULL_IMPORT, Seller.class.getSimpleName().toLowerCase())).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}
