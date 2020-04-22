package softuni.workshop.data.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    private String name;
    private Set<Project> projects;

    public Company() {
    }

    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
