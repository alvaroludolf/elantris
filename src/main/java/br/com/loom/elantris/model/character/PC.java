package br.com.loom.elantris.model.character;

public class PC extends Persona {

  private Classe classe;
  private Race race;

  public boolean complete() {
    return name != null && classe != null && race != null;
  }

  public Classe getClasse() {
    return classe;
  }

  public Race getRace() {
    return race;
  }

  public void setClasse(Classe classe) {
    this.classe = classe;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public void attack(Persona p) {
    if (p != null)
      p.takeDamage(classe.attack());
  }

  public void cast(Persona p) {
    if (p != null)
      p.takeDamage(classe.spell());
  }

  public void heal(Persona p) {
    if (p != null)
      p.healDamage(classe.heal());
  }

  @Override
  protected void die() {
  }

}
