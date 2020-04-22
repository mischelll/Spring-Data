package softuni.workshop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ProjectService projectService;
    private final XmlParser  xmlParser;

    public ImportController(EmployeeService employeeService, CompanyService companyService, ProjectService projectService, XmlParser xmlParser) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.projectService = projectService;
        this.xmlParser = xmlParser;
    }

    @GetMapping("/xml")
    public ModelAndView xmls() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");

        boolean[] areImported = new boolean[]{
                this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()
        };

        modelAndView.addObject("areImported", areImported);
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView companies() throws IOException {
        ModelAndView modelAndView = new ModelAndView("xml/import-companies");
        String xml = this.companyService.readCompaniesXmlFile();
        modelAndView.addObject("companies",xml);
        return modelAndView;
    }

    @GetMapping("/projects")
    public ModelAndView projects() {
        ModelAndView modelAndView = new ModelAndView("xml/import-projects");
        String xml = this.projectService.readProjectsXmlFile();
        modelAndView.addObject("projects",xml);
        return modelAndView;
    }

    @GetMapping("/employees")
    public ModelAndView employees() {
        ModelAndView modelAndView = new ModelAndView("xml/import-employees");
        String xml = this.employeeService.readEmployeesXmlFile();
        modelAndView.addObject("employees",xml);
        return modelAndView;
    }

    @PostMapping("/companies")
    public ModelAndView companiesConfirm() throws JAXBException {
        this.companyService.importCompanies();

        return this.redirect("/import/xml");
    }

    @PostMapping("/projects")
    public ModelAndView projectsConfirm() throws JAXBException {
       this.projectService.importProjects();

        return this.redirect("/import/xml");
    }

    @PostMapping("/employees")
    public ModelAndView employeesConfirm() throws JAXBException {
        this.employeeService.importEmployees();

        return this.redirect("/import/xml");
    }
}
