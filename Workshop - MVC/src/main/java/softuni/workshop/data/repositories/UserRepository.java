package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
