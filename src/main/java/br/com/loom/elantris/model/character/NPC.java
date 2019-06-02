package br.com.loom.elantris.model.character;

import br.com.loom.elantris.Log;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.MonsterSpec;

public class NPC extends Persona {

  private String abilities;

  public String getAbilities() {
    return abilities;
  }

  public void setAbilities(String abilities) {
    this.abilities = abilities;
  }

  public NPC(MonsterSpec spec) {
    this.setName(spec.getName());
    this.setHpMax(spec.getHp());
    this.setMpMax(spec.getMp());
    this.setXp(spec.getXp());
    this.setAbilities(spec.getAbilities());
  }

  public Action act() {
    if (!this.isDead() && site != null && site.hasPc()) {
      return () -> {
        attack(site.getPc());
        return true;
      };
    }
    return null;
  }

  @Override
  protected void die() {
  }

  public int health() {
    return hp * 4 / hpMax;
  }

  public void attack(Persona p) {
    if (p != null)
      p.takeDamage(10);
  }

  public void cast(Persona p) {
    if (p != null)
      p.takeDamage(100);
  }

  public void heal(Persona p) {
    if (p != null)
      p.healDamage(100);
  }

}
