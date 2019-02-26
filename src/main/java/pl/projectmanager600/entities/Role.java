package pl.projectmanager600.entities;

public enum Role {
  PROGRAMISTA("programista"),
  PROJECT_MANAGER("project manager"),
  TESTER("tester"),
  DUPA_WOLOWA("dupa wolowa");

  private String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
