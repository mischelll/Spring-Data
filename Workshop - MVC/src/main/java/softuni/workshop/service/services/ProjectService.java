package softuni.workshop.service.services;

import javax.xml.bind.JAXBException;

public interface ProjectService {

    void importProjects() throws JAXBException;

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjects();
}
