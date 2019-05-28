package br.com.loom.elantris;

import java.util.List;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;

public class Loop {

  public void tick(World world, List<Action> actions) {
    world.tick(actions);
    for (Action action : actions) {
      action.act();
    }
    actions.clear();
  }
}
