package br.com.loom.elantris.model;

import java.util.LinkedList;
import java.util.List;

public class Site {

  private World world;
  private int lat;
  private int lon;
  private SiteType type;
  private List<Feature> features = new LinkedList<>();
  private List<Props> props = new LinkedList<>();
  private List<Character> chars = new LinkedList<>();

  public Site(World world, int lat, int lon, SiteType type) {
    super();
    this.world = world;
    this.lat = lat;
    this.lon = lon;
    this.type = type;
  }

  public Site siteAt(Direction direction) {
    switch (direction) {
    case NORTH:
      return world.site(lat + 1, lon);
    case WEST:
      return world.site(lat, lon - 1);
    case SOUTH:
      return world.site(lat - 1, lon);
    case EAST:
      return world.site(lat, lon + 1);
    default:
      return this;
    }
  }

  public Site siteOpposedAt(Direction direction) {
    switch (direction) {
    case SOUTH:
      return world.site(lat + 1, lon);
    case EAST:
      return world.site(lat, lon - 1);
    case NORTH:
      return world.site(lat - 1, lon);
    case WEST:
      return world.site(lat, lon + 1);
    default:
      return this;
    }
  }

  public void leave(Character character) {
    chars.remove(character);
  }

  public void enter(Character character) {
    chars.add(character);
  }

  public int getLat() {
    return lat;
  }

  public int getLon() {
    return lon;
  }

  public SiteType getType() {
    return type;
  }

}
