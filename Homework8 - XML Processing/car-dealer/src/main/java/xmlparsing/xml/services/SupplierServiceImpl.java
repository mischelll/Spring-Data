package xmlparsing.xml.services;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.suppliers.SupplierDto;
import xmlparsing.xml.domain.dtos.suppliers.SupplierLocalViewDto;
import xmlparsing.xml.domain.dtos.suppliers.SupplierLocalViewRootDto;
import xmlparsing.xml.domain.dtos.suppliers.SupplierSeedRootDto;
import xmlparsing.xml.domain.entities.PartSupplier;
import xmlparsing.xml.repositories.SupplierRepository;
import xmlparsing.xml.services.interfaces.SupplierService;
import xmlparsing.xml.utils.interfaces.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static xmlparsing.xml.constants.FilePathConstants.*;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;

    public SupplierServiceImpl(ModelMapper modelMapper, SupplierRepository supplierRepository, XmlParser xmlParser) {
        this.modelMapper = modelMapper;
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedSuppliers() throws JAXBException {
        SupplierSeedRootDto seedRootDto = this.xmlParser.parseXml(SupplierSeedRootDto.class, SUPPLIERS_FILE_PATH);

        for (SupplierDto supplier : seedRootDto.getSuppliers()) {
            if (this.supplierRepository.findByName(supplier.getName()) == null) {
                PartSupplier map = this.modelMapper.map(supplier, PartSupplier.class);
                this.supplierRepository.saveAndFlush(map);

            }
        }

    }

    @Override
    public PartSupplier getRandomSupplier() {
        Random random = new Random();
        long number = this.supplierRepository.count();
        int i = random.nextInt((int) number) + 1;

        PartSupplier one = this.supplierRepository.getOne((long) i);

        return one;


    }

    @Override
    public SupplierLocalViewRootDto getLocalSuppliers() {
        List<PartSupplier> allLocalSuppliers = this.supplierRepository.getAllLocalSuppliers();
        List<SupplierLocalViewDto> collect = allLocalSuppliers.stream()
                .map(x -> this.modelMapper.map(x, SupplierLocalViewDto.class)).collect(Collectors.toList());

        SupplierLocalViewRootDto localViewRootDto = new SupplierLocalViewRootDto();
        localViewRootDto.setSuppliers(collect);
        return localViewRootDto;
    }
}
