package br.com.loom.elantris;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import br.com.loom.elantris.behaviour.CommandResolver;
import br.com.loom.elantris.helper.Log;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.site.Site;
import br.com.loom.elantris.model.site.SiteType;
import br.com.loom.elantris.repositories.SaveRepository;
import br.com.loom.elantris.repositories.TextRepository;
import br.com.loom.elantris.view.Screen;

public class ElantrisTest {

  private Scanner sc;
  private File dir;
  private File sav;
  private File bak;

  @Before
  public void setUp() throws IOException {
    TextRepository.instance = mock(TextRepository.class);
    doReturn(new String[] { "" }).when(TextRepository.instance).readResource(anyString());
    doReturn(new String[] { "Kobold;500;10;0;50;5" }).when(TextRepository.instance).readResource(eq("monsters.txt"));
    doReturn(new String[] { "createHelp" }).when(TextRepository.instance).readResource(eq("createHelp.ans"));
    doReturn(new String[] { "playHelp" }).when(TextRepository.instance).readResource(eq("playHelp.ans"));
    Log.pw = new PrintWriter(System.out);

    sc = new Scanner(new ByteArrayInputStream(new byte[0]));

    dir = Files.createTempDirectory(null).toFile();
    dir.delete();
    sav = new File(dir, "savegame.sav");
    bak = new File(dir, "savegame.bak");
  }

  @Test
  public void testSaveAndLoad() throws IOException {

    Screen screen = new Screen(System.out, 32, 80);
    SaveRepository saveRepository = new SaveRepository(dir);
    Elantris elantris = new Elantris(screen, sc, saveRepository);
    elantris.load();
    assertNull(elantris.pc);
    assertNull(elantris.world);

    World world = elantris.world();
    world.tick();
    world.tick();
    PC pc = elantris.pc();
    pc.setName("playerName");
    elantris.save();
    assertTrue(sav.exists());
    assertFalse(bak.exists());
    elantris.save();
    assertTrue(sav.exists());
    assertTrue(bak.exists());

    saveRepository = new SaveRepository(dir);
    elantris = new Elantris(screen, sc, saveRepository);
    elantris.load();

    assertEquals(world.time(), elantris.world().time());
    assertEquals(pc.getName(), elantris.pc().getName());

    sav.delete();
    bak.delete();
    dir.delete();

  }

  @Test
  public void testAddMessage() {

    Screen screen = new Screen(System.out, 32, 80);
    SaveRepository saveRepository = new SaveRepository(dir);
    Elantris elantris = new Elantris(screen, sc, saveRepository);
    elantris.addMessage("message1");

    assertEquals("message1", screen.getMessage()[2]);

    elantris.addMessage("message2");
    assertEquals("message1", screen.getMessage()[1]);
    assertEquals("message2", screen.getMessage()[2]);

    elantris.addMessage("message3");
    assertEquals("message1", screen.getMessage()[0]);
    assertEquals("message2", screen.getMessage()[1]);
    assertEquals("message3", screen.getMessage()[2]);
  }

  @Test
  public void testSetHelp() {

    Screen screen = new Screen(System.out, 32, 80);
    SaveRepository saveRepository = new SaveRepository(dir);
    Elantris elantris = new Elantris(screen, sc, saveRepository);

    elantris.setHelp(new String[] { "message1", "message2", "message3" });
    assertArrayEquals(new String[] { "message1", "message2", "message3" }, screen.getHelp());

    elantris.setHelp(new String[] {});
    assertArrayEquals(new String[] { "", "", "" }, screen.getHelp());

  }

  @Test
  public void testExecuteForWorldCreation() throws IOException {
    Screen screen = new Screen(System.out, 32, 80);
    SaveRepository saveRepository = new SaveRepository(dir);
    Scanner sc = new Scanner("command");

    Elantris elantris = new Elantris(screen, sc, saveRepository);

    elantris.commandResolver = mock(CommandResolver.class);
    Action action = new Action() {
      @Override
      public void act() {
      }
    };
    doReturn(action).when(elantris.commandResolver).resolve(any(), eq("command"));

    elantris.loop = mock(Loop.class);

    elantris.execute();

    verify(elantris.commandResolver).resolve(isNull(), eq("command"));
    verify(elantris.loop).tick(action);
    assertEquals("createHelp", screen.getHelp()[0]);

  }

  @Test
  public void testExecuteForPlayerCreation() throws IOException {
    Screen screen = new Screen(System.out, 32, 80);
    SaveRepository saveRepository = new SaveRepository(dir);
    Scanner sc = new Scanner("command");

    Elantris elantris = new Elantris(screen, sc, saveRepository);

    elantris.commandResolver = mock(CommandResolver.class);
    Action action = new Action() {
      @Override
      public void act() {
      }
    };
    doReturn(action).when(elantris.commandResolver).resolve(any(), eq("command"));

    elantris.loop = mock(Loop.class);

    PC pc = mock(PC.class);
    elantris.pc = pc;

    reset(elantris.commandResolver);
    reset(elantris.loop);
    elantris.execute();

    verify(elantris.commandResolver).resolve(eq(pc), eq("command"));
    verify(elantris.loop).tick(isNull());
    assertEquals("", screen.getHelp()[0]);

  }

  @Test
  public void testExecuteForPlay() throws IOException {
    Screen screen = new Screen(System.out, 32, 80);
    SaveRepository saveRepository = new SaveRepository(dir);
    Scanner sc = new Scanner("command");

    Elantris elantris = new Elantris(screen, sc, saveRepository);

    elantris.commandResolver = mock(CommandResolver.class);
    Action action = new Action() {
      @Override
      public void act() {
      }
    };
    doReturn(action).when(elantris.commandResolver).resolve(any(), eq("command"));

    elantris.loop = mock(Loop.class);
    
    World world = mock(World.class);
    elantris.world = world;

    PC pc = mock(PC.class);
    elantris.pc = pc;
    doReturn(new Site(world, 0,0, SiteType.PLAIN)).when(elantris.pc).getSite();

    doReturn(true).when(pc).complete();

    elantris.execute();

    verify(elantris.commandResolver).resolve(eq(pc), eq("command"));
    verify(elantris.loop).tick(action);
    assertEquals("playHelp", screen.getHelp()[0]);

  }

}
