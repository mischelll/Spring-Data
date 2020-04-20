package xmlparsing.xml.services.interfaces;


import org.springframework.stereotype.Service;
import xmlparsing.xml.domain.entities.Part;

import javax.xml.bind.JAXBException;
import java.util.Set;

@Service
public interface PartService {
    void seedParts() throws JAXBException;

    Set<Part> randomParts();

}
