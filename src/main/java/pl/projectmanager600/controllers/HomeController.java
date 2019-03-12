package pl.projectmanager600.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.projectmanager600.repositories.UserRepository;

@Controller
public class HomeController {

  private final UserRepository userRepository;

  @Autowired
  public HomeController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @RequestMapping("/logowanie")
  public String login() {
    return "login";
  }
}
