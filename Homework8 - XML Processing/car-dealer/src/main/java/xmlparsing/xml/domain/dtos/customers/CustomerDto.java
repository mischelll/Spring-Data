package xmlparsing.xml.domain.dtos.customers;

import xmlparsing.xml.domain.entities.Sale;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Set;
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerDto {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;


    public CustomerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }


}
