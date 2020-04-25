package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.OfferSeedDto;
import softuni.exam.models.dtos.OfferSeedRootDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static softuni.exam.constants.GlobalConstants.*;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper,
                            ValidationUtil validationUtil, XmlParser xmlParser, CarRepository carRepository, SellerRepository sellerRepository, PictureRepository pictureRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        String xml = "";

        try {
            xml = String.join("\n", Files.readAllLines(Paths.get(OFFERS_PATH_XML)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return xml;
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OfferSeedRootDto offerSeedRootDto = this.xmlParser.parseXml(OfferSeedRootDto.class, OFFERS_PATH_XML);
        StringBuilder stringBuilder = new StringBuilder();

        for (OfferSeedDto seedRootDto : offerSeedRootDto.getOfferSeedRootDtos()) {
            if (this.validationUtil.isValid(seedRootDto)) {
                LocalDateTime localDateTime = LocalDateTime.parse(seedRootDto.getAddedOn(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (this.offerRepository.findByAddedOnAndDescription(localDateTime, seedRootDto.getDescription()) == null) {
                    Offer offer = this.modelMapper.map(seedRootDto, Offer.class);
                    offer.setAddedOn(localDateTime);
                    offer.setCar(this.carRepository.findById(seedRootDto.getCar().getId()).orElse(null));
                    offer.setSeller(this.sellerRepository.findById(seedRootDto.getSeller().getId()).orElse(null));
                    System.out.println();

                    List<Picture> all = this.pictureRepository.findAll();
                    Set<Picture> pictures = new HashSet<>();
                    for (Picture picture : all) {
                        if (offer.getCar() != null && picture.getCar() != null) {

                            Long id = offer.getCar().getId();
                            Long id1 = picture.getCar().getId();
                            if (id1.equals(id)) {
                                pictures.add(picture);
                            }
                        }
                    }

                    Car car = null;
                    if (offer.getCar() != null) {

                         car = this.carRepository.findById(offer.getCar().getId()).orElse(null);
                    }
                    if (car != null) {

                        car.setPictures(pictures);

                        this.carRepository.saveAndFlush(car);
                    }

                    this.offerRepository.saveAndFlush(offer);
                    stringBuilder.append(String.format(SUCCESSFULL_IMPORT_OFFER, offer.getAddedOn().toString(), offer.getHasGoldStatus())).append(System.lineSeparator());
                }
            } else {
                stringBuilder.append(String.format(UNSUCCESSFULL_IMPORT, Offer.class.getSimpleName().toLowerCase())).append(System.lineSeparator());
            }
        }


        return stringBuilder.toString();
    }
}
