package pl.projectmanager600.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.projectmanager600.entities.Role;
import pl.projectmanager600.entities.User;
import pl.projectmanager600.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public AuthenticationService(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().toUpperCase()));

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
  }
}
