package br.com.loom.elantris.behaviour;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.loom.elantris.Elantris;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.site.Site;
import br.com.loom.elantris.model.site.SiteType;

public class CommandResolverActionTest {

  @Mock
  private Elantris elantris;
  @Mock
  private World world;
  @Mock
  private NPC npc;

  @Before
  public void setup() {

    MockitoAnnotations.initMocks(this);
    doReturn(world).when(elantris).world();
    doReturn(new Site(world, 0, 0, SiteType.PLAIN)).when(world).site(anyInt(), anyInt());

    List<NPC> npcs = new LinkedList<NPC>();
    npcs.add(npc);

    doReturn(npcs).when(world).npcs();
  }

  @Test
  public void testPcMovement() throws IOException {

    Site site = new Site(world, 0, 0, SiteType.PLAIN);
    site.enter(npc);

    CommandResolver resolver = new CommandResolver(elantris);

    PC pc = mock(PC.class);
    doReturn(true).when(pc).complete();
    doReturn(site).when(pc).getSite();

    Action action = resolver.resolve(pc, "w");
    action.act();
    verify(pc).moveForward();

    action = resolver.resolve(pc, "s");
    action.act();
    verify(pc).moveBackward();

    action = resolver.resolve(pc, "q");
    action.act();
    verify(pc).moveLeft();

    action = resolver.resolve(pc, "e");
    action.act();
    verify(pc).moveRight();

    action = resolver.resolve(pc, "a");
    action.act();
    verify(pc).turnLeft();

    action = resolver.resolve(pc, "d");
    action.act();
    verify(pc).turnRight();

  }

  @Test
  public void testPcAttacks() throws IOException {

    Site site = new Site(world, 0, 0, SiteType.PLAIN);
    site.enter(npc);

    CommandResolver resolver = new CommandResolver(elantris);

    PC pc = mock(PC.class);
    doReturn(true).when(pc).complete();
    doReturn(site).when(pc).getSite();

    Action action = resolver.resolve(pc, "1");
    action.act();
    verify(pc).attack(eq(npc));

    action = resolver.resolve(pc, "2");
    action.act();
    verify(pc).cast(eq(npc));

    action = resolver.resolve(pc, "3");
    action.act();
    verify(pc).heal(eq(pc));

  }
  @Test
  public void testSaveLoadReset() throws IOException {

    Site site = new Site(world, 0, 0, SiteType.PLAIN);
    site.enter(npc);

    CommandResolver resolver = new CommandResolver(elantris);

    PC pc = mock(PC.class);
    doReturn(true).when(pc).complete();
    doReturn(site).when(pc).getSite();

    Action action = resolver.resolve(pc, "t");
    assertNull(action);
    verify(elantris).save();;

    action = resolver.resolve(pc, "y");
    assertNull(action);
    verify(elantris).load();

    action = resolver.resolve(pc, "u");
    assertNull(action);

  }


}
