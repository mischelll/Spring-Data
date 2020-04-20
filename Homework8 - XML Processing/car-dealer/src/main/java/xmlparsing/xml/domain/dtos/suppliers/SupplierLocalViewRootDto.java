package xmlparsing.xml.domain.dtos.suppliers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierLocalViewRootDto {
    @XmlElement
    private List<SupplierLocalViewDto> suppliers;

    public SupplierLocalViewRootDto() {
    }

    public List<SupplierLocalViewDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierLocalViewDto> suppliers) {
        this.suppliers = suppliers;
    }
}
