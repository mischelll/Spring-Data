package xmlparsing.xml.domain.dtos.customers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSalesRootDto {
    @XmlElement
    private List<CustomerSalesDto> customers;

    public CustomerSalesRootDto() {
    }

    public List<CustomerSalesDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerSalesDto> customers) {
        this.customers = customers;
    }
}
