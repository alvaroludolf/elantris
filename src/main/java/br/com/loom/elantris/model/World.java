package br.com.loom.elantris.model;

import java.util.LinkedList;
import java.util.List;

public class World {

  private long time = 0;
  private Site[][] sites = new Site[1000][1000];
  private List<NPC> npcs = new LinkedList<>();

  public World() {
  }

  public long getTime() {
    return time;
  }

  public Site site(int lat, int lon) {
    if (sites[lat][lon] == null) {
      sites[lat][lon] = new Site(lat, lon, SiteType.PLAIN);
    }
    return sites[lat][lon];
  }

  public void tick(List<Action> actions) {
    for (NPC npc : npcs) {
      Action[] npcActions = npc.act();
      for (Action action : npcActions) {
        actions.add(action);
      }
    }
    time++;
  }

}
