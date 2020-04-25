package softuni.exam.models.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedDto {
    @XmlElement(name = "first-name")
    @Length(min = 2, max = 19, message = "Invalid seller first name!")
    private String firstName;

    @XmlElement(name = "last-name")
    @Length(min = 2, max = 19, message = "Invalid seller last name!")
    private String lastName;

    @XmlElement
    @Email(message = "Inavlid email!")
    private String email;

    @XmlElement
    @NotNull
    @NotEmpty
    @NotBlank

    private String rating;

    @XmlElement
    private String town;

    public SellerSeedDto() {
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
