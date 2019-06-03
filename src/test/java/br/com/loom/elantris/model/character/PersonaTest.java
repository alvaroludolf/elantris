package br.com.loom.elantris.model.character;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.loom.elantris.model.site.Site;

public class PersonaTest {

  @Mock
  Site site;

  @Before
  public void setUp() throws IOException {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testDropAt() {
    PC persona = new PC();
    persona.dropAt(site, Direction.NORTH);

    verify(site).enter(persona);
    assertEquals(Direction.NORTH, persona.direction());
    assertEquals(site, persona.getSite());
  }

  @Test
  public void testHpLimit() {
    PC persona = new PC();
    persona.setHpMax(100);
    persona.setHp(120);
    assertEquals(100, persona.getHp());

    persona.setHpMax(100);
    persona.setHp(-10);
    assertEquals(0, persona.getHp());

    persona.setHpMax(100);
    persona.setHp(80);
    assertEquals(80, persona.getHp());

  }

  @Test
  public void testMpLimit() {
    PC persona = new PC();
    persona.setMpMax(100);
    persona.setMp(120);
    assertEquals(100, persona.getMp());

    persona.setMpMax(100);
    persona.setMp(-20);
    assertEquals(0, persona.getMp());

    persona.setMpMax(100);
    persona.setMp(80);
    assertEquals(80, persona.getMp());

  }

  @Test
  public void testTakeAndHealDamage() {
    PC persona = new PC();
    persona.setHpMax(100);
    persona.setHp(100);
    persona.takeDamage(20);
    assertEquals(80, persona.getHp());
    persona.healDamage(10);
    assertEquals(90, persona.getHp());
  }

  @Test
  public void testTurn() {
    PC persona = new PC();
    persona.dropAt(site, Direction.NORTH);
    persona.turnRight();
    assertEquals(Direction.EAST, persona.direction());
    persona.turnRight();
    assertEquals(Direction.SOUTH, persona.direction());
    persona.turnRight();
    assertEquals(Direction.WEST, persona.direction());
    persona.turnRight();
    assertEquals(Direction.NORTH, persona.direction());
    persona.turnLeft();
    assertEquals(Direction.WEST, persona.direction());
    persona.turnLeft();
    assertEquals(Direction.SOUTH, persona.direction());
    persona.turnLeft();
    assertEquals(Direction.EAST, persona.direction());
    persona.turnLeft();
    assertEquals(Direction.NORTH, persona.direction());
  }

  @Test
  public void testMove() {
    PC persona = new PC();

    Site north = mock(Site.class);
    doReturn(north).when(site).siteAt(Direction.NORTH);

    Site south = mock(Site.class);
    doReturn(south).when(north).siteOpposedAt(Direction.NORTH);

    Site east = mock(Site.class);
    doReturn(east).when(south).siteAt(Direction.EAST);

    Site west = mock(Site.class);
    doReturn(west).when(east).siteAt(Direction.WEST);

    persona.dropAt(site, Direction.NORTH);

    persona.moveForward();

    verify(site).leave(persona);
    verify(north).enter(persona);
    assertEquals(north, persona.getSite());
    assertEquals(Direction.NORTH, persona.direction());

    persona.moveBackward();

    verify(north).leave(persona);
    verify(south).enter(persona);
    assertEquals(south, persona.getSite());
    assertEquals(Direction.NORTH, persona.direction());

    persona.moveRight();

    verify(south).leave(persona);
    verify(east).enter(persona);
    assertEquals(east, persona.getSite());
    assertEquals(Direction.NORTH, persona.direction());

    persona.moveLeft();

    verify(east).leave(persona);
    verify(west).enter(persona);
    assertEquals(west, persona.getSite());
    assertEquals(Direction.NORTH, persona.direction());

  }

}
