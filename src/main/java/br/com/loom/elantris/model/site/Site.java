package br.com.loom.elantris.model.site;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.Character;
import br.com.loom.elantris.model.character.Direction;
import br.com.loom.elantris.model.character.NPC;

public class Site implements Serializable {

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

  public Site siteAtDistance(Direction direction, int distance) {
    switch (direction) {
    case NORTH:
      return world.site(lat + distance, lon);
    case WEST:
      return world.site(lat, lon - distance);
    case SOUTH:
      return world.site(lat - distance, lon);
    case EAST:
      return world.site(lat, lon + distance);
    default:
      return this;
    }
  }

  public Site siteAt(Direction direction) {
    return siteAtDistance(direction, 1);
  }

  public Site siteOpposedAt(Direction direction) {
    switch (direction) {
    case SOUTH:
      return siteAt(Direction.NORTH);
    case EAST:
      return siteAt(Direction.WEST);
    case NORTH:
      return siteAt(Direction.SOUTH);
    case WEST:
      return siteAt(Direction.EAST);
    default:
      return this;
    }
  }

  public void leave(Character character) {
    chars.remove(character);
  }

  public void enter(Character character) {
    chars.add(character);
    for (int i = -3; i < 4; i++) {
      world.addMonster(world.site(lat + 6, lon + i));
      world.addMonster(world.site(lat - 6, lon + i));
      world.addMonster(world.site(lat + i, lon + 6));
      world.addMonster(world.site(lat + i, lon - 6));
    }

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

  public void addNpc(NPC npc) {
    chars.add(npc);
  }

  public boolean hasNpc() {
    for (Character character : chars) {
      if (character instanceof NPC)
        return true;
    }
    return false;
  }

}
