package xmlparsing.xml.services.interfaces;


import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.dtos.suppliers.SupplierLocalViewRootDto;
import xmlparsing.xml.domain.entities.PartSupplier;

import javax.xml.bind.JAXBException;

@Service
public interface SupplierService {
    void seedSuppliers() throws JAXBException;

    PartSupplier getRandomSupplier();

    SupplierLocalViewRootDto getLocalSuppliers();
}
