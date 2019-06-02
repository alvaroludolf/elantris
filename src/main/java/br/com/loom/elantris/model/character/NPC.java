package br.com.loom.elantris.model.character;

import br.com.loom.elantris.model.MonsterSpec;

public class NPC extends Persona {

  private int attack;

  public NPC(MonsterSpec spec) {
    this.setName(spec.getName());
    this.setHpMax(spec.getHp());
    this.setMpMax(spec.getMp());
    this.setXp(spec.getXp());
    this.attack = spec.getAttack();
  }

  public int health() {
    return hp * 4 / hpMax;
  }

  public int attack(Persona p) {
    if (p != null) {
      int damage = (int) (Math.random() * (attack + 1));
      p.takeDamage(damage);
      return damage;
    }
    return 0;
  }

  public int cast(Persona p) {
    if (p != null) {
      p.takeDamage(0);
    }
    return 0;
  }

  public int heal(Persona p) {
    if (p != null) {
      p.healDamage(0);
    }
    return 0;
  }

}
