package softuni.exam.models.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private SellerRating rating;
    private String town;

    private Set<Offer> offers;

    public Seller() {
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller")
    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    @Column(name = "first_name")
    @Size(min = 2, max = 19, message = "Invalid seller first name!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    @Size(min = 2, max = 19, message = "Invalid seller last name!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    public SellerRating getRating() {
        return rating;
    }

    public void setRating(SellerRating rating) {
        this.rating = rating;
    }

    @Column(nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
