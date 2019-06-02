package br.com.loom.elantris;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import br.com.loom.elantris.behaviour.CommandResolver;
import br.com.loom.elantris.helper.Log;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.repositories.SaveRepository;
import br.com.loom.elantris.repositories.TextRepository;
import br.com.loom.elantris.view.Screen;

/**
 * Kind of controller for the game, but also its bootstrap.
 *
 */
public class Elantris {

  public static void main(String[] args) {

    try {

      Screen screen = new Screen(System.out, 32, 80);
      Scanner sc = new Scanner(System.in);

      Elantris elantris = new Elantris(screen, sc, new SaveRepository(new File("save")));
      elantris.execute();

      sc.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected Screen screen;
  protected Scanner sc;
  protected SaveRepository saveRepository;

  protected PC pc;
  protected World world;
  protected CommandResolver commandResolver;
  protected Loop loop;

  public Elantris(Screen screen, Scanner sc, SaveRepository saveRepository) {
    super();
    this.screen = screen;
    this.sc = sc;
    this.saveRepository = saveRepository;
    this.commandResolver = new CommandResolver(this);
    this.loop = new Loop(this);
  }

  public void execute() throws IOException {
    String cmd;

    screen.draw(world, pc);
    while (sc.hasNext()) {
      cmd = sc.next();
      Action playerAction = commandResolver.resolve(pc, cmd);
      loop.tick(playerAction);

      if (pc == null || (pc.complete() && pc.isDead())) {
        setHelp(TextRepository.instance().readResource("createHelp.ans"));
      } else if (pc != null && pc.complete() && !pc.isDead()) {
        setHelp(TextRepository.instance().readResource("playHelp.ans"));
      } else {
        setHelp("");
      }

      screen.draw(world, pc);
    }
  }

  public void addMessage(String m) {
    screen.addMessage(m);
  }

  public void setHelp(String... h) {
    screen.setHelp(h);
  }

  public void save() {
    try {
      saveRepository.save(this);
      addMessage("Game SAVED.");
    } catch (RuntimeException | IOException e) {
      Log.log(e);
      addMessage("Error saving the game");
      addMessage(e.getMessage());
    }
  }

  public void load() {
    try {
      saveRepository.load(this);
      addMessage("Game LOADED.");
    } catch (RuntimeException | IOException e) {
      Log.log(e);
      addMessage("Error loading the game");
      addMessage(e.getMessage());
    }
  }

  public void restart() {
    pc = null;
    world = null;
  }

  public PC pc() {
    if (pc == null)
      pc = new PC();
    return pc;
  }

  public void pc(PC pc) {
    this.pc = pc;
  }

  public World world() {
    if (world == null)
      world = new World();
    return world;
  }

  public void world(World world) {
    this.world = world;
  }

}
