package br.com.loom.elantris;

import java.util.List;
import java.util.Scanner;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.Direction;
import br.com.loom.elantris.model.PC;
import br.com.loom.elantris.model.World;

public class Elantris {

  public static void main(String[] args) {

    try {

      Screen screen = new Screen(System.out, 32, 80);
      Scanner sc = new Scanner(System.in);

      Elantris elantris = new Elantris();
      elantris.execute(screen, sc);

      sc.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void execute(Screen screen, Scanner sc) {
    String cmd;
    Loop loop = new Loop();
    CommandResolver resolver = new CommandResolver();

    World world = new World();
    PC pc = new PC(world.site(500, 500), Direction.NORTH);

    while (sc.hasNext()) {
      screen.draw(world, pc);
      cmd = sc.next();
      List<Action> playerActions = resolver.resolve(pc, cmd);
      loop.tick(world, playerActions);
    }
  }
}
