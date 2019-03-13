package pl.projectmanager600.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projectmanager600.entities.Status;
import pl.projectmanager600.entities.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  Optional<Task> findWithCommentsById(Long id);

  List<Task> findAllByStatus(Status status);
}
