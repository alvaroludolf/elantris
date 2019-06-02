package br.com.loom.elantris.model.character;

import java.io.Serializable;

import br.com.loom.elantris.model.Interactable;
import br.com.loom.elantris.model.site.Site;

public abstract class Persona implements Interactable, Serializable {

  protected Site site;
  protected Direction direction;
  protected long xp;
  protected int hpMax;
  protected int hp;
  protected int mpMax;
  protected int mp;
  protected String name;

  public Direction getDirection() {
    return direction;
  }

  public int getHp() {
    return hp;
  }

  public int getHpMax() {
    return hpMax;
  }

  public int getMp() {
    return mp;
  }

  public int getMpMax() {
    return mpMax;
  }

  public Site getSite() {
    return site;
  }

  public long getXp() {
    return xp;
  }

  public void dropAt(Site site, Direction direction) {
    this.site = site;
    this.direction = direction;
    this.site.enter(this);
  }

  public void move(Site to) {
    site.leave(this);
    to.enter(this);
    this.site = to;
  }

  public void moveBackward() {
    move(this.site.siteOpposedAt(this.direction));
  }

  public void moveForward() {
    move(this.site.siteAt(this.direction));
  }

  public void moveLeft() {
    turnLeft();
    moveForward();
    turnRight();
  }

  public void moveRight() {
    turnRight();
    moveForward();
    turnLeft();
  }

  public void setHpMax(int hpMax) {
    this.hp = hpMax;
    this.hpMax = hpMax;
  }

  public void setMpMax(int mpMax) {
    this.mp = mpMax;
    this.mpMax = mpMax;
  }

  public void setXp(long xp) {
    this.xp = xp;
  }

  public void turnLeft() {
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

  public void turnRight() {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void takeDamage(int attack) {
    setHp(hp - attack);
  }

  public void healDamage(int heal) {
    setHp(hp + heal);
  }

  protected void setHp(int hp) {
    if (hp <= 0) {
      this.hp = 0;
    } else if (hp > hpMax) {
      this.hp = hpMax;
    } else {
      this.hp = hp;
    }
  }

  protected void setMp(int mp) {
    if (mp <= 0) {
      this.mp = 0;
    } else if (mp > mpMax) {
      this.mp = mpMax;
    } else {
      this.mp = mp;
    }
  }

  public boolean isDead() {
    return hp <= 0;
  }

  @Override
  public String toString() {
    return "Persona [site=" + site + ", name=" + name + "]";
  }

}
