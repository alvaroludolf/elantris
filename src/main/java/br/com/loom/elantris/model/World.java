package br.com.loom.elantris.model;

import java.util.LinkedList;
import java.util.List;

public class World {

  private static final int SITE_VARIANCE = SiteType.values().length;

  private long time = 0;
  private Site[][] sites = new Site[1000][1000];
  private List<NPC> npcs = new LinkedList<>();

  public World() {
  }

  public long getTime() {
    return time;
  }

  public Site site(int lat, int lon) {

    if (!safeSite(lat, lon))
      return null;

    SiteType type;
    Site nearbySite;
    int[] nearby = new int[SITE_VARIANCE];
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (safeSite(lat, lon) && (nearbySite = sites[lat + y][lon + x]) != null) {
          nearby[nearbySite.getType().ordinal()]++;
        }
      }
    }
    int winner = 0;
    for (int i = 0; i < nearby.length; i++) {
      if (nearby[i] > nearby[winner])
        winner = i;
      else if (nearby[i] > nearby[winner])
        if (Math.random() < .5)
          winner = i;
    }
    if (Math.random() < .1) {
      winner = (int) (Math.random() * SITE_VARIANCE);
    }
    type = SiteType.values()[winner];

    if (sites[lat][lon] == null) {
      sites[lat][lon] = new Site(this, lat, lon, type);
    }
    return sites[lat][lon];
  }

  public List<Action> requestNpcActions() {
    List<Action> actions = new LinkedList<>();
    for (NPC npc : npcs) {
      Action[] npcActions = npc.act();
      for (Action action : npcActions) {
        actions.add(action);
      }
    }
    return actions;
  }
  
  public void tick() {
    time++;
  }

  protected boolean safeSite(int lat, int lon) {
    return (lat >= 0 && lat < sites.length && lon >= 0 && lon < sites[lat].length);
  }

}
