package xmlparsing.xml.domain.entities;

import xmlparsing.xml.domain.entities.BaseEntity;
import xmlparsing.xml.domain.entities.PartSupplier;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Integer quantity;

    private PartSupplier partSupplier;
    private Set<Car> cars;


    @ManyToMany(mappedBy = "parts",fetch = FetchType.EAGER,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    public Part() {

    }

    @ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.ALL)
    public PartSupplier getPartSupplier() {
        return partSupplier;
    }

    public void setPartSupplier(PartSupplier partSupplier) {
        this.partSupplier = partSupplier;
    }



    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
