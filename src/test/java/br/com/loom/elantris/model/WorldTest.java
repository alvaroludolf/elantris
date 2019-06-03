package br.com.loom.elantris.model;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.loom.elantris.model.site.Site;

public class WorldTest {

  @Test
  public void testSite() {
    World world = new World();
    Site site = world.site(10, 20);
    assertEquals(10, site.getLat());
    assertEquals(20, site.getLon());
  }

  @Test
  public void testTick() {
    World world = new World();
    assertEquals(0, world.time());
    world.tick();
    assertEquals(1, world.time());
    world.tick();
    assertEquals(2, world.time());
  }

}
