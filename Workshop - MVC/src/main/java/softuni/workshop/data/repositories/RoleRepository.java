package softuni.workshop.data.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByAuthority(String role);
}
