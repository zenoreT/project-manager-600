package pl.projectmanager600.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    logRepository.save(new Log(principal.getName(), "stworzył nowy komentarz o treści: " + content + ", do zadania: " + task.getName()));

    return ResponseEntity.ok().body(Collections.singletonMap("comment", content));
  }

  @GetMapping("/logs")
  public ResponseEntity<?> getLogs() {
    return ResponseEntity.ok(logRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
  }
}
