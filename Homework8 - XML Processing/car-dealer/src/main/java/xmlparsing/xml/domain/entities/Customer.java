package xmlparsing.xml.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private LocalDateTime birthDate;
    private Boolean isYoungDriver;
    private Set<Sale> purchases;

    public Customer() {
    }

    @Column
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    public Set<Sale> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Sale> purchases) {
        this.purchases = purchases;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date_of_birth")
    public LocalDateTime getDateOfBirth() {
        return birthDate;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.birthDate = dateOfBirth;
    }

    @Column(name = "is_young_driver")
    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
