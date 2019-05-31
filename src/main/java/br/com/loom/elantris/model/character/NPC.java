package br.com.loom.elantris.model.character;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.MonsterSpec;

public class NPC extends Character {

  private String abilities;

  public String getAbilities() {
    return abilities;
  }

  public void setAbilities(String abilities) {
    this.abilities = abilities;
  }

  public NPC(MonsterSpec spec) {
    this.setHpMax(spec.getHp());
    this.setMpMax(spec.getMp());
    this.setXp(spec.getXp());
    this.setAbilities(spec.getAbilities());
  }

  public Action[] act() {
    return null;
  }

}
