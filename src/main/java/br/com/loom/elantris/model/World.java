package br.com.loom.elantris.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.TextResourceManager;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.site.Site;
import br.com.loom.elantris.model.site.SiteType;

public class World implements Serializable {

  private static final int SITE_VARIANCE = SiteType.values().length;

  private long time = 0;
  private Site[][] sites = new Site[1000][1000];
  private List<NPC> npcs = new LinkedList<>();
  private List<MonsterSpec> specs = new LinkedList<>();
  int totalChance = 0;

  public World() throws IOException {
    String[] monsterList = TextResourceManager.instance().readResource("monsters.txt");
    for (String monster : monsterList) {
      if (!monster.startsWith("#")) {
        MonsterSpec spec = new MonsterSpec(monster);
        specs.add(spec);
        totalChance += spec.getChance();
      }
    }
  }

  public void addMonster(Site site) {
    if (Math.random() < .05) {
      int monsterRandom = (int) (Math.random() * totalChance);
      for (MonsterSpec spec : specs) {
        monsterRandom -= spec.getChance();
        if (monsterRandom < 0) {
          NPC npc = new NPC(spec);
          site.addNpc(npc);
        }
      }
    }
  }

  public long getTime() {
    return time;
  }

  public Site site(int lat, int lon) {

    if (!safeSite(lat, lon))
      return null;

    if (sites[lat][lon] == null) {
      SiteType type = defineSiteType(lat, lon);
      Site site = new Site(this, lat, lon, type);
      addMonster(site);
      sites[lat][lon] = site;
    }
    return sites[lat][lon];
  }

  private SiteType defineSiteType(int lat, int lon) {
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
    return type;
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
