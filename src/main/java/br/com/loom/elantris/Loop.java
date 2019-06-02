package br.com.loom.elantris;

import java.util.List;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;

public class Loop {

  public void tick(World world, Action pcActions) {
    if (world == null || pcActions == null)
      return;

    List<Action> npcActions = world.requestNpcActions();

    boolean worldTick = pcActions.act();
    if (worldTick) {
      for (Action action : npcActions) {
        if (action != null)
          action.act();
      }
      world.tick();
    }
  }
}
