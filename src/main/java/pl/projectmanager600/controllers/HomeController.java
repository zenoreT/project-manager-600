package pl.projectmanager600.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projectmanager600.entities.*;
import pl.projectmanager600.repositories.CommentRepository;
import pl.projectmanager600.repositories.LogRepository;
import pl.projectmanager600.repositories.TaskRepository;
import pl.projectmanager600.repositories.UserRepository;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Controller
public class HomeController {

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final LogRepository logRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public HomeController(UserRepository userRepository, TaskRepository taskRepository,
                        LogRepository logRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

    this.userRepository = userRepository;
    this.taskRepository = taskRepository;
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
  public String registerUser(User user, Model model, BindingResult bindingResult) {
    if(bindingResult.hasErrors()) {
      return "register";
    }
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

    model.addAttribute("users", userRepository.findAllUsernames());
    model.addAttribute("task", new Task());
    model.addAttribute("logs", logRepository.findAll(Sort.by(Sort.Order.desc("id"))));
    model.addAttribute("tasks", tasks);
    model.addAttribute("statuses", Status.values());

    return "home";
  }

  @GetMapping("/my-tasks")
  public String myHomePage(Model model, Principal principal) {

    List<Task> toDoTasks = taskRepository.findAssigneesTasksByStatus(Status.TO_DO, principal.getName());
    List<Task> inProgressTasks = taskRepository.findAssigneesTasksByStatus(Status.IN_PROGRESS, principal.getName());
    List<Task> doneTasks = taskRepository.findAssigneesTasksByStatus(Status.DONE, principal.getName());

    List<List<Task>> tasks = new ArrayList<>();
    tasks.add(toDoTasks);
    tasks.add(inProgressTasks);
    tasks.add(doneTasks);

    model.addAttribute("users", userRepository.findAllUsernames());
    model.addAttribute("task", new Task());
    model.addAttribute("logs", logRepository.findAll(Sort.by(Sort.Order.desc("id"))));
    model.addAttribute("tasks", tasks);
    model.addAttribute("statuses", Status.values());

    return "home";
  }

  @PostMapping("/tasks/new")
  public String addTask(Task task, Principal principal) {
    Optional<User> assignee = userRepository.findByUsername(task.getAssignee().getUsername());
    if(!assignee.isPresent()) {
      return "/error";
    }
    task.setAssignee(assignee.get());
    task.setStatus(Status.TO_DO);
    taskRepository.save(task);
    logRepository.save(new Log(principal.getName(), "stworzył nowe zadanie: " + task.getName()));

    return "redirect:/home";
  }

  @PostMapping("/tasks/changeAssignee")
  public String addTask(Long id, String username, Principal principal) {
    Optional<Task> taskOptional = taskRepository.findById(id);
    Optional<User> assigneeOptional = userRepository.findByUsername(username);
    if(!taskOptional.isPresent() || !assigneeOptional.isPresent()) {
      return "/error";
    }
    Task task = taskOptional.get();
    User assignee = assigneeOptional.get();
    task.setAssignee(assignee);

    taskRepository.save(task);
    logRepository.save(new Log(principal.getName(), "zmienił osobę odpowiedzialną za zadanie: " + task.getName() + ", na: " + assignee.getUsername()));

    return "redirect:/home";
  }

  @PostMapping("/tasks/changeStatus")
  public String addTask(Long id, Status status, String date, Principal principal) {
    Optional<Task> taskOptional = taskRepository.findById(id);
    if(!taskOptional.isPresent()) {
      return "/error";
    }
    Task task = taskOptional.get();
    task.setStatus(status);
    if(status == Status.DONE && !date.isEmpty()) {
      task.setEndDate(Date.valueOf(date));
    }

    taskRepository.save(task);
    logRepository.save(new Log(principal.getName(), "zmienił status zadania: " + task.getName() + ", na: " + status.getName()));

    return "redirect:/home";
  }

  @PostMapping("/tasks/delete")
  public String deleteTask(Long id, Principal principal) {
    Optional<Task> taskOptional = taskRepository.findById(id);
    if(!taskOptional.isPresent()) {
      return "/error";
    }
    Task task = taskOptional.get();

    logRepository.save(new Log(principal.getName(), "usunął  zadanie: " + task.getName()));
    taskRepository.delete(task);

    return "redirect:/home";
  }
}
