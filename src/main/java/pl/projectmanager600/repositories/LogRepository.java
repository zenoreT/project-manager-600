package pl.projectmanager600.repositories;

  import org.springframework.data.jpa.repository.JpaRepository;
  import org.springframework.stereotype.Repository;
  import pl.projectmanager600.entities.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
