package br.com.loom.elantris.model.character;

public enum Classe {

  WARRIOR(20, -20, 12, 0, 4),
  MAGE(-20, 20, 4, 12, 6),
  PRIEST(0, 0, 6, 8, 12);

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
    return (int) (Math.random() * (attack + 1));
  }

  public int heal() {
    return heal + (int) (Math.random() * (heal + 1));
  }

  public int spell() {
    return (spell / 2) + (int) (Math.random() * (spell + 1));
  }

  public int maxHp() {
    return maxHp;
  }

  public int maxMp() {
    return maxMp;
  }

}
