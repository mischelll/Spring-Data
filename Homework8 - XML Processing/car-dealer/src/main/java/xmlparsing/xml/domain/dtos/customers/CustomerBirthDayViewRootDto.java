package xmlparsing.xml.domain.dtos.customers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerBirthDayViewRootDto {
    @XmlElement(name = "customer")
    private List<CustomerBirthDayViewDto> customers;


    public CustomerBirthDayViewRootDto() {
    }

    public List<CustomerBirthDayViewDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerBirthDayViewDto> customers) {
        this.customers = customers;
    }
}
