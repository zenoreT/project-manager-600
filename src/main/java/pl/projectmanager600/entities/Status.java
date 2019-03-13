package pl.projectmanager600.entities;

public enum Status {
  TO_DO("Do zrobienia"),
  IN_PROGRESS("W trakcie"),
  DONE("Zakończone");

  private String name;

  Status(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
