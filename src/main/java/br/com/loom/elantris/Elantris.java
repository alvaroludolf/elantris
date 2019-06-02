package br.com.loom.elantris;

import java.io.IOException;
import java.util.Scanner;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.character.Persona;

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
    screen.addMessage(m);
  }

  public void setHelp(String... h) {
    screen.setHelp(h);
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

  public Persona pc() {
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
    CommandResolver resolver = new CommandResolver(this);
    Loop loop = new Loop();

    screen.draw(world, pc);
    while (sc.hasNext()) {
      cmd = sc.next();
      Action playerAction = resolver.resolve(pc, cmd);
      if (playerAction != null) {
        loop.tick(world, playerAction);
      }
      screen.draw(world, pc);
    }
  }

}
