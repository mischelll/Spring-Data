package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException;

    <T> void exportToXml(T object, Class<T> objectClass, String path) throws JAXBException;

}
