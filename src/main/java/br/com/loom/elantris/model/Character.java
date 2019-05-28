package br.com.loom.elantris.model;

public abstract class Character implements Interactable {

  private Site site;
  private Direction direction;

  public Character(Site site, Direction direction) {
    super();
    this.site = site;
    this.direction = direction;
  }

  public Site getSite() {
    return site;
  }

  public Direction getDirection() {
    return direction;
  }

}
