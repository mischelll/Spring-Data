package xmlparsing.xml.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class PartSupplier extends BaseEntity {
    private String name;
    private Boolean isImporter;

    private Set<Part> parts;

    public PartSupplier() {
    }

    @OneToMany(mappedBy = "partSupplier",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_importer")
    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }
}
