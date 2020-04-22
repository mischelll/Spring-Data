package softuni.workshop.data.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
    private String authority;
    private Set<User> users;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    @ManyToMany(mappedBy = "authorities", targetEntity = User.class)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Column(nullable = false,unique = true)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
