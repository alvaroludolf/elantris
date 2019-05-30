package br.com.loom.elantris.model;

public class PC extends Character {

  private String name;
  private Classe classe;
  private Race race;
  private long XP;
  private int hpMax;
  private int hp;
  private int mpMax;
  private int mp;

  private int completeness = 0;

  public boolean complete() {
    return completeness >= 3;
  }

  public int completeness() {
    return completeness;
  }

  public Classe getClasse() {
    return classe;
  }

  public int getHpMax() {
    return hpMax;
  }

  public int getMpMax() {
    return mpMax;
  }

  public String getName() {
    return name;
  }

  public Race getRace() {
    return race;
  }

  public void setClasse(Classe classe) {
    increaseCompleteness();
    this.classe = classe;
  }

  public void setHpMax(int hpMax) {
    this.hp = hpMax;
    this.hpMax = hpMax;
  }

  public void setMpMax(int mpMax) {
    this.mp = mpMax;
    this.mpMax = mpMax;
  }

  public void setName(String name) {
    increaseCompleteness();
    this.name = name;
  }

  public void setRace(Race race) {
    increaseCompleteness();
    this.race = race;
  }

  private void increaseCompleteness() {
    this.completeness++;
  }

}
