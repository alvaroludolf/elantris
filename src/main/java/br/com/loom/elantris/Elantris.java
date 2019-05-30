package br.com.loom.elantris;

import java.util.List;
import java.util.Scanner;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.PC;
import br.com.loom.elantris.model.World;

public class Elantris {

  private PC pc;
  private Screen screen;
  private Scanner sc;
  private World world;

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

  public Elantris(Screen screen, Scanner sc) {
    super();
    this.screen = screen;
    this.sc = sc;
  }

  public void addMessage(String m) {
    screen.getMessage()[0] = screen.getMessage()[1];
    screen.getMessage()[1] = m;
  }

  public PC pc() {
    if (pc == null)
      pc = new PC();
    return pc;
  }

  public World world() {
    if (world == null)
      world = new World();
    return world;
  }

  private void execute() {
    String cmd;
    Loop loop = new Loop();
    CommandResolver resolver = new CommandResolver(this);

    world = new World();

    screen.draw(world, pc);
    while (sc.hasNext()) {
      cmd = sc.next();
      List<Action> playerActions = resolver.resolve(pc, cmd);
      loop.tick(world, playerActions);
      screen.draw(world, pc);
    }
  }
}
