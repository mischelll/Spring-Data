package xml.productshop.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xml.productshop.demo.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u from User as u " +
            "join Product as p on p.seller.id = u.id " +
            "where p.buyer is not null " +
            "and size(u.sold) >=1 " +
            "group by u.id order by u.firstName,u.lastName")
    List<User> getAllBySoldSizeAndBuyerNotNull();



}