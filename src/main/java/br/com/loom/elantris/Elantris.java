package br.com.loom.elantris;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.Character;
import br.com.loom.elantris.model.character.PC;

public class Elantris {

  public static void main(String[] args) {

    try {

      Screen screen = new Screen(System.out, 32, 80);
      Scanner sc = new Scanner(System.in);

      Elantris elantris = new Elantris(screen, sc);
      elantris.execute();

      sc.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Screen screen;
  private Scanner sc;
  private SaveRepository saveRepository = new SaveRepository();

  private PC pc;
  private World world;

  public Elantris(Screen screen, Scanner sc) {
    super();
    this.screen = screen;
    this.sc = sc;
  }

  public void addMessage(String m) {
    screen.getMessage()[0] = screen.getMessage()[1];
    screen.getMessage()[1] = screen.getMessage()[2];
    screen.getMessage()[2] = m;
  }

  public void save() {
    try {
      saveRepository.save(this);
      addMessage("Game SAVED.");
    } catch (IOException e) {
      addMessage("Error saving the game");
      addMessage(e.getMessage());
    }
  }

  public void load() {
    try {
      saveRepository.load(this);
      addMessage("Game LOADED.");
    } catch (IOException e) {
      addMessage("Error loading the game");
      addMessage(e.getMessage());
    }
  }

  public Character pc() {
    if (pc == null)
      pc = new PC();
    return pc;
  }

  public void pc(PC pc) {
    this.pc = pc;
  }

  public World world() throws IOException {
    if (world == null)
      world = new World();
    return world;
  }

  public void world(World world) {
    this.world = world;
  }

  private void execute() throws IOException {
    String cmd;
    Loop loop = new Loop();
    CommandResolver resolver = new CommandResolver(this);

    screen.draw(world, pc);
    while (sc.hasNext()) {
      cmd = sc.next();
      List<Action> playerActions = resolver.resolve(pc, cmd);
      loop.tick(world, playerActions);
      screen.draw(world, pc);
    }
  }

}
