package br.com.loom.elantris.model.character;

public class PC extends Character {

  private String name;
  private Classe classe;
  private Race race;
  public boolean complete() {
    return name != null && classe != null && race != null;
  }

  public Classe getClasse() {
    return classe;
  }

  public String getName() {
    return name;
  }

  public Race getRace() {
    return race;
  }

  public void setClasse(Classe classe) {
    this.classe = classe;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRace(Race race) {
    this.race = race;
  }

}
