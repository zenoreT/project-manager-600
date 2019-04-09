package pl.projectmanager600.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.projectmanager600.entities.Comment;
import pl.projectmanager600.entities.Log;
import pl.projectmanager600.entities.Task;
import pl.projectmanager600.entities.User;
import pl.projectmanager600.repositories.CommentRepository;
import pl.projectmanager600.repositories.LogRepository;
import pl.projectmanager600.repositories.TaskRepository;
import pl.projectmanager600.repositories.UserRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;

@RestController
public class RestApiController {

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final CommentRepository commentRepository;
  private final LogRepository logRepository;

  @Autowired
  public RestApiController(UserRepository userRepository, TaskRepository taskRepository,
                           CommentRepository commentRepository, LogRepository logRepository) {

    this.userRepository = userRepository;
    this.taskRepository = taskRepository;
    this.commentRepository = commentRepository;
    this.logRepository = logRepository;
  }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") Long id) {
        Task task = taskRepository.findWithCommentsById(id).orElseThrow(RuntimeException::new);

        return ResponseEntity.ok().body(Collections.singletonMap("task", task));
    }

  @PostMapping("/comments/new")
  public ResponseEntity<?> addComment(String content, Long taskId, Principal principal) {
    User author = userRepository.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
    Task task = taskRepository.findById(taskId).orElseThrow(RuntimeException::new);

    Comment comment = new Comment();
    comment.setContent(content);
    comment.setAuthor(author);
    comment.setCreationDate(new Date());
    comment.setTask(task);

    commentRepository.save(comment);
    Log log = new Log(principal.getName(), "stworzył nowy komentarz o treści: " + content + ".\n" + task.getName());
    logRepository.save(log);

    return ResponseEntity.ok().body(new JSONObject().put("content", comment.getContent()).put("author", comment.getAuthor().getUsername()).put("creationDate", comment.getCreationDate()).put("log", log.getContent()).toString());
  }

  @GetMapping("/logs")
  public ResponseEntity<?> getLogs() {
    return ResponseEntity.ok(logRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
  }
}
