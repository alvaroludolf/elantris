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

public class NpcResolverTest {

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
  public void stabilityOfNullInput() throws IOException {

    NpcResolver resolver = new NpcResolver();
    
    NPC npc = mock(NPC.class);

    Action action = resolver.resolve(npc);
    assertNull(action);

  }

}
