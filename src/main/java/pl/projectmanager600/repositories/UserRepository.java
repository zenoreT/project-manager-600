package pl.projectmanager600.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projectmanager600.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
