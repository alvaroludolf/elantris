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
import br.com.loom.elantris.model.character.Classe;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.character.Race;
import br.com.loom.elantris.model.site.Site;
import br.com.loom.elantris.model.site.SiteType;

public class CommandResolverTest {

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

    CommandResolver resolver = new CommandResolver(elantris);

    Action action = resolver.resolve(null, null);
    assertNull(action);

    action = resolver.resolve(null, "");
    assertNull(action);

    action = resolver.resolve(new PC(), null);
    assertNull(action);

  }

  @Test
  public void createNewHumanWarrior() throws IOException {

    CommandResolver resolver = new CommandResolver(elantris);
    PC pc = new PC();
    doReturn(pc).when(elantris).pc();

    Action action = resolver.resolve(null, "1");
    assertNull(action);
    verify(elantris).pc();
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "characterName");
    assertNull(action);
    assertEquals("characterName", pc.getName());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "1");
    assertNull(action);
    assertEquals(Classe.WARRIOR, pc.getClasse());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "1");
    assertNull(action);
    assertEquals(Race.HUMAN, pc.getRace());
    assertTrue(pc.complete());

  }

  @Test
  public void createNewElfMage() throws IOException {

    CommandResolver resolver = new CommandResolver(elantris);
    PC pc = new PC();
    doReturn(pc).when(elantris).pc();

    Action action = resolver.resolve(null, "1");
    assertNull(action);
    verify(elantris).pc();
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "characterName");
    assertNull(action);
    assertEquals("characterName", pc.getName());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "2");
    assertNull(action);
    assertEquals(Classe.MAGE, pc.getClasse());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "2");
    assertNull(action);
    assertEquals(Race.ELF, pc.getRace());
    assertTrue(pc.complete());

  }

  @Test
  public void createNewDwarfPriest() throws IOException {

    CommandResolver resolver = new CommandResolver(elantris);
    PC pc = new PC();
    doReturn(pc).when(elantris).pc();

    Action action = resolver.resolve(null, "1");
    assertNull(action);
    verify(elantris).pc();
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "characterName");
    assertNull(action);
    assertEquals("characterName", pc.getName());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "3");
    assertNull(action);
    assertEquals(Classe.PRIEST, pc.getClasse());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "3");
    assertNull(action);
    assertEquals(Race.DWARF, pc.getRace());
    assertTrue(pc.complete());

  }

  @Test
  public void createNewInvalidClass() throws IOException {

    CommandResolver resolver = new CommandResolver(elantris);
    PC pc = new PC();
    doReturn(pc).when(elantris).pc();

    Action action = resolver.resolve(null, "1");
    assertNull(action);
    verify(elantris).pc();
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "characterName");
    assertNull(action);
    assertEquals("characterName", pc.getName());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "4");
    assertNull(action);
    assertNull(pc.getClasse());
    assertFalse(pc.complete());

  }

  @Test
  public void createNewInvalidRace() throws IOException {

    CommandResolver resolver = new CommandResolver(elantris);
    PC pc = new PC();
    doReturn(pc).when(elantris).pc();

    Action action = resolver.resolve(null, "1");
    assertNull(action);
    verify(elantris).pc();
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "characterName");
    assertNull(action);
    assertEquals("characterName", pc.getName());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "3");
    assertNull(action);
    assertEquals(Classe.PRIEST, pc.getClasse());
    assertFalse(pc.complete());

    action = resolver.resolve(pc, "4");
    assertNull(action);
    assertNull(pc.getRace());
    assertFalse(pc.complete());

  }
  
  @Test
  public void loadSavefile() throws IOException {
    CommandResolver resolver = new CommandResolver(elantris);
    resolver.resolve(null, "2");
    verify(elantris).load();
  }

}
