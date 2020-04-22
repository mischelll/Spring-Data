package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.companies.CompaniesRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class CompanyServiceImpl implements CompanyService {
    private static final String PATH = "src/main/resources/files/xmls/companies.xml";

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importCompanies() throws JAXBException {
        CompaniesRootDto companiesRootDto = this.xmlParser.parseXml(CompaniesRootDto.class, PATH);

        companiesRootDto.getCompanies().forEach(companiesDto -> {
            Company map = this.modelMapper.map(companiesDto, Company.class);
            this.companyRepository.save(map);
        });
    }

    @Override
    public boolean areImported() {

        return companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        String xml = "";
        try {
             xml = String.join("\n", Files.readAllLines(Paths.get(PATH)));
        } catch (IOException e) {
            e.getMessage();
        }

        return xml;
    }
}
