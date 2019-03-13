package pl.projectmanager600.entities;

import javax.persistence.*;

@Entity
@Table(name = "logs")
public class Log {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "content", nullable = false)
  private String content;
}
