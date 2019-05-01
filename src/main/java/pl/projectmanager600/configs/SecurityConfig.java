package pl.projectmanager600.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final AuthenticationService authenticationService;

  @Autowired
  public SecurityConfig(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/home").authenticated()
      .antMatchers("/my-tasks").authenticated()
      .antMatchers("/comments/new").authenticated()
      .antMatchers("/css/**").permitAll()
      .antMatchers("/js/**").permitAll()
      .and()
      .formLogin().loginPage("/login")
      .loginProcessingUrl("/login")
      .defaultSuccessUrl("/home")
      .permitAll()
      .and()
      .logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/")
      .clearAuthentication(true)
      .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
      .invalidateHttpSession(true)
      .permitAll()
      .and()
      .csrf()
      .ignoringAntMatchers("/logout")
      .ignoringAntMatchers("/comments/new");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}