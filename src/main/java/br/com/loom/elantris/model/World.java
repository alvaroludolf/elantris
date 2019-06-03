package br.com.loom.elantris.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import br.com.loom.elantris.model.character.Direction;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.site.Site;
import br.com.loom.elantris.model.site.SiteType;
import br.com.loom.elantris.repositories.TextRepository;

public class World implements Serializable {

  private static final int SITE_VARIANCE = SiteType.values().length;

  private long time = 0;
  private Site[][] sites = new Site[1000][1000];
  private List<NPC> npcs = new LinkedList<>();
  private List<MonsterSpec> specs = new LinkedList<>();
  private int totalChance = 0;
  
  Random r = new Random();

  public World() {
    String[] monsterList = TextRepository.instance().readResource("monsters.txt");
    for (String monster : monsterList) {
      if (!monster.startsWith("#")) {
        MonsterSpec spec = new MonsterSpec(monster);
        specs.add(spec);
        totalChance += spec.getChance();
      }
    }
  }

  public NPC addMonster(Site site) {
    if (site != null && r.nextDouble() < .05) {
      int monsterRandom = (int) (r.nextDouble() * totalChance);
      for (MonsterSpec spec : specs) {
        monsterRandom -= spec.getChance();
        if (monsterRandom < 0) {
          NPC npc = new NPC(spec);
          npc.dropAt(site, Direction.NORTH);
          npcs.add(npc);
          return npc;
        }
      }
    }
    return null;
  }

  public List<NPC> npcs() {
    return npcs;
  }

  public long time() {
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

  public void tick() {
    time++;
  }

  protected SiteType defineSiteType(int lat, int lon) {
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
        if (r.nextDouble() < .5)
          winner = i;
    }
    if (r.nextDouble() < .1) {
      winner = (int) (r.nextDouble() * SITE_VARIANCE);
    }
    type = SiteType.values()[winner];
    return type;
  }

  protected boolean safeSite(int lat, int lon) {
    return (lat >= 0 && lat < sites.length && lon >= 0 && lon < sites[lat].length);
  }

}
