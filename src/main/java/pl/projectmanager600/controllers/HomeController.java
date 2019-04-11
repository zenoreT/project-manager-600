package pl.projectmanager600.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projectmanager600.entities.*;
import pl.projectmanager600.repositories.CommentRepository;
import pl.projectmanager600.repositories.LogRepository;
import pl.projectmanager600.repositories.TaskRepository;
import pl.projectmanager600.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolationException;

@Controller
public class HomeController {

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final CommentRepository commentRepository;
  private final LogRepository logRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public HomeController(UserRepository userRepository, TaskRepository taskRepository,
      CommentRepository commentRepository, LogRepository logRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

    this.userRepository = userRepository;
    this.taskRepository = taskRepository;
    this.commentRepository = commentRepository;
    this.logRepository = logRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @GetMapping("/")
  public String indexPage(Principal principal) {
    if (principal != null) {
      return "redirect:/home";
    }

    return "index";
  }

  @RequestMapping("/login")
  public String loginPage(Principal principal) {
    if (principal != null) {
      return "redirect:/home";
    }

    return "login";
  }

  @GetMapping("/register")
  public String registerPage(Model model) {
    model.addAttribute("roles", Role.values());
    model.addAttribute("user", new User());

    return "register";
  }

  @PostMapping("/register")
  public String registerUser(Model model, User user) {
    Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    if (!pattern.matcher(user.getPassword()).matches()) {
      return "redirect:/register?error";
    }
    model.addAttribute("roles", Role.values());

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

    try {
      userRepository.save(user);
    } catch (Exception e) {
      return "redirect:/register?error";
    }

    return "redirect:/login";
  }

  @GetMapping("/home")
  public String homePage(Model model) {
    List<Task> toDoTasks = taskRepository.findAllByStatus(Status.TO_DO);
    List<Task> inProgressTasks = taskRepository.findAllByStatus(Status.IN_PROGRESS);
    List<Task> doneTasks = taskRepository.findAllByStatus(Status.DONE);

    List<List<Task>> tasks = new ArrayList<>();
    tasks.add(toDoTasks);
    tasks.add(inProgressTasks);
    tasks.add(doneTasks);

    model.addAttribute("task", new Task());
    model.addAttribute("logs", logRepository.findAll(Sort.by(Sort.Order.desc("id"))));
    model.addAttribute("tasks", tasks);
    model.addAttribute("statuses", Status.values());

    return "home";
  }

  @PostMapping("/tasks/new")
  public String addTask(Task task, Principal principal) {
    task.setAssignee(
        userRepository.findByUsername(task.getAssignee().getUsername()).orElseThrow(RuntimeException::new));
    task.setStatus(Status.TO_DO);
    taskRepository.save(task);
    logRepository.save(new Log(principal.getName(), "stworzył nowe zadanie: " + task.getName()));

    return "redirect:/home";
  }

  @PostMapping("/tasks/changeAssignee")
  public String addTask(Long id, String username, Principal principal) {
    Task task = taskRepository.findById(id).orElseThrow(RuntimeException::new);
    User assignee = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    task.setAssignee(assignee);

    taskRepository.save(task);
    logRepository.save(new Log(principal.getName(), "zmienił osobę odpowiedzialną za zadanie: " + task.getName() + ", na: " + assignee.getUsername()));

    return "redirect:/home";
  }

  @PostMapping("/tasks/changeStatus")
  public String addTask(Long id, Status status, Principal principal) {
    Task task = taskRepository.findById(id).orElseThrow(RuntimeException::new);
    task.setStatus(status);

    taskRepository.save(task);
    logRepository.save(new Log(principal.getName(), "zmienił status zadania: " + task.getName() + ", na: " + status.getName()));

    return "redirect:/home";
  }
}
