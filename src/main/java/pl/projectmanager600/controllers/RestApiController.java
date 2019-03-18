//package pl.projectmanager600.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pl.projectmanager600.entities.Comment;
//import pl.projectmanager600.entities.Task;
//import pl.projectmanager600.repositories.CommentRepository;
//import pl.projectmanager600.repositories.LogRepository;
//import pl.projectmanager600.repositories.TaskRepository;
//
//@RestController
//public class RestApiController {
//
//  private final TaskRepository taskRepository;
//  private final CommentRepository commentRepository;
//  private final LogRepository logRepository;
//
//  @Autowired
//  public RestApiController(TaskRepository taskRepository, CommentRepository commentRepository,
//                           LogRepository logRepository) {
//
//    this.taskRepository = taskRepository;
//    this.commentRepository = commentRepository;
//    this.logRepository = logRepository;
//  }
//
//  @PutMapping("/tasks")
//  public ResponseEntity<?> addTask(Task task) {
//    taskRepository.save(task);
//    return ResponseEntity.ok().build();
//  }
//
//  @PutMapping("/comments")
//  public ResponseEntity<?> addComment(Comment comment) {
//    commentRepository.save(comment);
//    return ResponseEntity.ok().build();
//  }
//
//  @GetMapping("/logs")
//  public ResponseEntity<?> getLogs() {
//    return ResponseEntity.ok(logRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
//  }
//}
