package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.projects.ProjectsRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;

    private static final String PATH = "src/main/resources/files/xmls/projects.xml";

    public ProjectServiceImpl(ModelMapper modelMapper, XmlParser xmlParser, ProjectRepository projectRepository, CompanyRepository companyRepository) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectsRootDto projectsRootDto = this.xmlParser.parseXml(ProjectsRootDto.class, PATH);

        projectsRootDto.getProjectDtos()
                .forEach(projectDto -> {
                    Project map = this.modelMapper.map(projectDto, Project.class);
                    Company byName = this.companyRepository.findByName(map.getCompany().getName());
                    map.setCompany(byName);
                    this.projectRepository.saveAndFlush(map);
                });
    }

    @Override
    public boolean areImported() {

        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {
        String xml = "";
        try {
            xml = String.join("\n", Files.readAllLines(Paths.get(PATH)));
        } catch (IOException e) {
            e.getMessage();
        }

        return xml;
    }

    @Override
    public String exportFinishedProjects() {
        List<Project> allByIsFinished =
                this.projectRepository.findAllByFinishedTrue();

        StringBuilder stringBuilder = new StringBuilder();

        for (Project project : allByIsFinished) {
            stringBuilder.append(String.format("Project Name: %s%n    Description: %s%n    %.2f%n",
                    project.getName(),
                    project.getDescription(),
                    project.getPayment()));
        }
        return stringBuilder.toString();
    }
}
