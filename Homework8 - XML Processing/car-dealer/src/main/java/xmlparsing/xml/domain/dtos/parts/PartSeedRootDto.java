package xmlparsing.xml.domain.dtos.parts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartSeedRootDto {
    @XmlElement(name = "part")
    private List<PartDto> partDtoList;

    public PartSeedRootDto() {
    }

    public List<PartDto> getPartDtoList() {
        return partDtoList;
    }

    public void setPartDtoList(List<PartDto> partDtoList) {
        this.partDtoList = partDtoList;
    }
}
