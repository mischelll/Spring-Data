package xmlparsing.xml.domain.dtos.cars;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarMakeViewRootDto {

    @XmlElement
    private List<CarMakeViewDto> cars;

    public CarMakeViewRootDto() {
    }

    public List<CarMakeViewDto> getCars() {
        return cars;
    }

    public void setCars(List<CarMakeViewDto> cars) {
        this.cars = cars;
    }
}
