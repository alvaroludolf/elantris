package br.com.loom.elantris.model.character;

public enum Classe {

  WARRIOR(100, 0, 12, 4, 4),
  MAGE(20, 100, 4, 12, 6),
  PRIEST(60, 80, 6, 8, 12);

  private int maxHp;

  private int maxMp;
  private int attack;
  private int spell;
  private int heal;

  private Classe(int maxHp, int maxMp, int attack, int spell, int heal) {
    this.maxHp = maxHp;
    this.maxMp = maxMp;
    this.attack = attack;
    this.spell = spell;
    this.heal = heal;
  }

  public int attack() {
    return attack;
  }

  public int heal() {
    return heal;
  }

  public int maxHp() {
    return maxHp;
  }

  public int maxMp() {
    return maxMp;
  }

  public int spell() {
    return spell;
  }

}
