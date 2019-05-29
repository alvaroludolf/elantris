package br.com.loom.elantris.model;

public abstract class Character implements Interactable {

  protected Site site;
  protected Direction direction;

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

  public void move(Site to) {
    site.leave(this);
    to.enter(this);
    this.site = to;
  }

  public void moveForward() {
    move(this.site.siteAt(this.direction));
  }

  public void moveBackward() {
    move(this.site.siteOpposedAt(this.direction));
  }

  public void lookLeft() {
    switch (direction) {
    case NORTH:
      direction = Direction.WEST;
      break;
    case WEST:
      direction = Direction.SOUTH;
      break;
    case SOUTH:
      direction = Direction.EAST;
      break;
    case EAST:
      direction = Direction.NORTH;
    }
  }

  public void lookRight() {
    switch (direction) {
    case NORTH:
      direction = Direction.EAST;
      break;
    case WEST:
      direction = Direction.NORTH;
      break;
    case SOUTH:
      direction = Direction.WEST;
      break;
    case EAST:
      direction = Direction.SOUTH;
    }
  }

}
