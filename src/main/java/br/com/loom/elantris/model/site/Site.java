package br.com.loom.elantris.model.site;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.Log;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.Direction;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.character.Persona;

public class Site implements Serializable {

  private World world;
  private int lat;
  private int lon;
  private SiteType type;
  private List<Feature> features = new LinkedList<>();
  private List<Props> props = new LinkedList<>();
  private List<Persona> chars = new LinkedList<>();

  public Site(World world, int lat, int lon, SiteType type) {
    super();
    this.world = world;
    this.lat = lat;
    this.lon = lon;
    this.type = type;
  }

  public void enter(Persona persona) {
    Log.log(persona + " entering " + this);
    chars.add(persona);
    if (persona instanceof PC) {
      for (int i = -3; i < 4; i++) {
        world.addMonster(world.site(lat + 6, lon + i));
        world.addMonster(world.site(lat - 6, lon + i));
        world.addMonster(world.site(lat + i, lon + 6));
        world.addMonster(world.site(lat + i, lon - 6));
      }
    }
  }

  public void leave(Persona persona) {
    Log.log(persona + " leaving " + this);
    chars.remove(persona);
  }

  public int getLat() {
    return lat;
  }

  public int getLon() {
    return lon;
  }

  public NPC getNpc() {
    for (Persona persona : chars) {
      if (persona instanceof NPC)
        return (NPC) persona;
    }
    return null;
  }

  public PC getPc() {
    for (Persona persona : chars) {
      if (persona instanceof PC)
        return (PC) persona;
    }
    return null;
  }

  public SiteType getType() {
    return type;
  }

  public boolean hasNpc() {
    for (Persona persona : chars) {
      if (persona instanceof NPC)
        return true;
    }
    return false;
  }

  public boolean hasPc() {
    for (Persona persona : chars) {
      if (persona instanceof PC)
        return true;
    }
    return false;
  }

  public Site siteAt(Direction direction) {
    return siteAtDistance(direction, 1);
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

  @Override
  public String toString() {
    return "Site [lat=" + lat + ", lon=" + lon + ", type=" + type + "]";
  }

}
