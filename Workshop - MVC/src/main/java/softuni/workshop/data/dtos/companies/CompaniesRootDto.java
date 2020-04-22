package softuni.workshop.data.dtos.companies;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompaniesRootDto {
    @XmlElement(name = "company")
    private List<CompaniesDto> companies;

    public CompaniesRootDto() {
    }

    public List<CompaniesDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompaniesDto> companies) {
        this.companies = companies;
    }
}
