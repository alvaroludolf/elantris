package br.com.loom.elantris;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.loom.elantris.behaviour.NpcResolver;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.site.Site;

public class LoopTest {

  @Mock
  private Elantris elantris;
  @Mock
  private NpcResolver npcResolver;
  @Mock
  private World world;
  @Mock
  private PC pc;
  @Mock
  private NPC npc;

  @Before
  public void setup() {

    MockitoAnnotations.initMocks(this);
    doReturn(world).when(elantris).world();

    List<NPC> npcs = new LinkedList<NPC>();
    npcs.add(npc);

    doReturn(npcs).when(world).npcs();
    doReturn(pc).when(elantris).pc();
    doReturn(mock(Site.class)).when(pc).getSite();
  }

  @Test
  public void testLoopCallingResolver() {
    Loop loop = new Loop(elantris);
    loop.npcResolver = npcResolver;
    loop.tick(null);
    verify(world, never()).tick();

    Action pcAction = mock(Action.class);
    Action npcAction = mock(Action.class);
    doReturn(npcAction).when(loop.npcResolver).resolve(any());

    loop.tick(pcAction);
    verify(world).tick();
    verify(npcResolver).resolve(npc);
    verify(npcAction).act();

  }

  @Test
  public void testDetectingDeath() {
    Loop loop = new Loop(elantris);
    loop.npcResolver = npcResolver;
    doReturn(true).when(pc).complete();
    doReturn(true).when(pc).isDead();

    Action pcAction = mock(Action.class);
    Action npcAction = mock(Action.class);
    doReturn(npcAction).when(loop.npcResolver).resolve(any());

    loop.tick(pcAction);
    assertNull(elantris.pc);

  }

}
