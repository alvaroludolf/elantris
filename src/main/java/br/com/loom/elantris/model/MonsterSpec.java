package br.com.loom.elantris.model;

import java.io.Serializable;

public class MonsterSpec implements Serializable {

  private String name;
  private int hp;
  private int mp;
  private int xp;
  private int attack;
  private int chance;

  public MonsterSpec(String spec) {
    String[] attr = spec.split(";");
    this.name = attr[0];
    this.chance = Integer.parseInt(attr[1]);
    this.hp = Integer.parseInt(attr[2]);
    this.mp = Integer.parseInt(attr[3]);
    this.xp = Integer.parseInt(attr[4]);
    this.attack = Integer.parseInt(attr[5]);
  }

  public int getAttack() {
    return attack;
  }

  public int getChance() {
    return chance;
  }

  public int getHp() {
    return hp;
  }

  public int getMp() {
    return mp;
  }

  public String getName() {
    return name;
  }

  public int getXp() {
    return xp;
  }

}
