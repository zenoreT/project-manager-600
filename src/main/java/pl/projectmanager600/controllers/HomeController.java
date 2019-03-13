package pl.projectmanager600.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projectmanager600.entities.Role;
import pl.projectmanager600.entities.Status;
import pl.projectmanager600.entities.Task;
import pl.projectmanager600.entities.User;
import pl.projectmanager600.repositories.TaskRepository;
import pl.projectmanager600.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  //  private final CommentRepository commentRepository;
//  private final LogRepository logRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public HomeController(UserRepository userRepository, TaskRepository taskRepository/*,
                        CommentRepository commentRepository, LogRepository logRepository*/,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {

    this.userRepository = userRepository;
    this.taskRepository = taskRepository;
//    this.commentRepository = commentRepository;
//    this.logRepository = logRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @GetMapping("/")
  public String indexPage(Principal principal) {
    if(principal != null) {
      return "redirect:/home";
    }

    return "index";
  }

  @RequestMapping("/login")
  public String loginPage(Principal principal) {
    if(principal != null) {
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
    model.addAttribute("roles", Role.values());

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);

    return "register";
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

    model.addAttribute("tasks", tasks);
    model.addAttribute("statuses", Status.values());

    return "home";
  }
}
