package pl.projectmanager600.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.projectmanager600.entities.Status;
import pl.projectmanager600.entities.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  Optional<Task> findWithCommentsById(Long id);

  List<Task> findAllByStatus(Status status);

  @Query("SELECT t FROM Task t WHERE t.status = :status AND t.assignee.username = :assignee")
  List<Task> findAssigneesTasksByStatus(@Param("status") Status status, @Param("assignee") String assignee);
}
