package xmlparsing.xml.services;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.parts.PartDto;
import xmlparsing.xml.domain.dtos.parts.PartSeedRootDto;
import xmlparsing.xml.domain.entities.Part;
import xmlparsing.xml.repositories.PartRepository;
import xmlparsing.xml.services.interfaces.PartService;
import xmlparsing.xml.services.interfaces.SupplierService;
import xmlparsing.xml.utils.interfaces.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;

import java.util.*;

import static xmlparsing.xml.constants.FilePathConstants.*;

@Service
@Transactional
public class PartServiceImpl implements PartService {
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;
    private final SupplierService supplierService;
    private final XmlParser xmlParser;

    public PartServiceImpl(ModelMapper modelMapper, PartRepository partRepository, SupplierService supplierService, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
        this.supplierService = supplierService;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedParts() throws JAXBException {
        PartSeedRootDto partSeedRootDto = this.xmlParser.parseXml(PartSeedRootDto.class, PARTS_FILE_PATH);

        for (PartDto partDto : partSeedRootDto.getPartDtoList()) {
            if (this.partRepository.findByNameAndPrice(partDto.getName(), partDto.getPrice()) == null) {
                Part map1 = this.modelMapper.map(partDto, Part.class);
                this.partRepository.saveAndFlush(map1);
            }


        }

        setSuppliers();
    }

    @Override
    public Set<Part> randomParts() {
        Random random = new Random();
        int randomCounter = (int) (Math.random() * ((20 - 10) + 1)) + 10;
        Set<Part> parts = new HashSet<>();

        for (int i = 0; i < randomCounter; i++) {
            long randomId = random.nextInt((int) this.partRepository.count()) + 1;
            Part one = this.partRepository.getOne(randomId);

            parts.add(one);
        }


        return parts;
    }
    private void setSuppliers(){
        List<Part> all = this.partRepository.findAll();
        all.stream().forEach(part -> {


            part.setPartSupplier(this.supplierService.getRandomSupplier());
        });
    }
}
