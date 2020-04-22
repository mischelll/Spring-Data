package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.employees.EmployeeRootDto;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    private static final String PATH = "src/main/resources/files/xmls/employees.xml";

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, XmlParser xmlParser, ModelMapper modelMapper, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = this.xmlParser.parseXml(EmployeeRootDto.class, PATH);

        employeeRootDto.getEmployeeDtoList().forEach(employeeDto -> {
            Employee map = this.modelMapper.map(employeeDto, Employee.class);
            map.setProject(this.projectRepository.findByName(employeeDto.getProject().getName()));
            this.employeeRepository.saveAndFlush(map);
            System.out.println();
        });
    }

    @Override
    public boolean areImported() {

        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        String xml = "";
        try {
            xml = String.join("\n", Files.readAllLines(Paths.get(PATH)));
        } catch (IOException e) {
            e.getMessage();
        }

        return xml;
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Employee> allByAgeGreaterThan = this.employeeRepository.findAllByAgeGreaterThan(25);

        for (Employee employee : allByAgeGreaterThan) {
            stringBuilder.append(String.format("Name: %s%n    Age: %d%n    Project name: %s%n",
                    employee.getFirstName() + " " + employee.getLastName(),
                    employee.getAge(),
                    employee.getProject().getName()));
        }
        return stringBuilder.toString();
    }
}
