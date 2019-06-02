package br.com.loom.elantris.model.character;

public enum Race {

  HUMAN(100, 100), ELF(80, 120), DWARF(120, 100);

  private int maxHp;

  private int maxMp;

  private Race(int maxHp, int maxMp) {
    this.maxHp = maxHp;
    this.maxMp = maxMp;
  }

  public int maxHp() {
    return maxHp;
  }

  public int maxMp() {
    return maxMp;
  }

}
