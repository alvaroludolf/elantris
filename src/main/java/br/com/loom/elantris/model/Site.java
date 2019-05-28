package br.com.loom.elantris.model;

import java.util.LinkedList;
import java.util.List;

public class Site {

  private int lat;
  private int lon;
  private SiteType type;
  private List<Feature> features = new LinkedList<>();
  private List<Props> props = new LinkedList<>();
  private List<NPC> npcs = new LinkedList<>();

  public Site(int lat, int lon, SiteType type) {
    super();
    this.lat = lat;
    this.lon = lon;
    this.type = type;
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
