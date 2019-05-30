package br.com.loom.elantris;

import java.util.List;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;

public class Loop {

  public void tick(World world, List<Action> pcActions) {
    if (pcActions == null)
      return;

    List<Action> npcActions = world.requestNpcActions();

    boolean worldTick = false;
    for (Action action : pcActions) {
      worldTick |= action.act();
    }

    if (worldTick) {
      for (Action action : npcActions) {
        action.act();
      }
    }
  }
}
