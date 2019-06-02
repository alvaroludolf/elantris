package br.com.loom.elantris.model.character;

public class PC extends Persona {

  private Classe classe;
  private Race race;

  public int attack(Persona p) {
    if (p != null) {
      int damage = classe.attack();
      p.takeDamage(damage);
      if (p.isDead())
        xp += p.getXp();
      return damage;
    }
    return 0;
  }

  public int cast(Persona p) {
    if (mp >= 5) {
      setMp(mp - 5);
      if (p != null) {
        int damage = classe.spell();
        p.takeDamage(damage);
        if (p.isDead())
          xp += p.getXp();
        return damage;
      }
    }
    return 0;
  }

  public int heal(Persona p) {
    if (mp >= 5) {
      setMp(mp - 5);
      if (p != null) {
        int heal = classe.heal();
        p.healDamage(heal);
        return heal;
      }
    }
    return 0;
  }

  public int rest() {
    int timeToRest = Math.max(getMpMax() - getMp(), getHpMax() - getHp());
    setHp(Integer.MAX_VALUE);
    setMp(Integer.MAX_VALUE);
    return timeToRest;
  }

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

}
