package br.com.loom.elantris.model;

public abstract class NPC extends Character {

  public NPC(Site site, Direction direction) {
    super(site, direction);
  }

  public abstract Action[] act();

}
