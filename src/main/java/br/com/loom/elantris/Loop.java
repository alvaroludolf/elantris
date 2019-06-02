package br.com.loom.elantris;

import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.behaviour.NpcResolver;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;

public class Loop {

  protected NpcResolver npcResolver;
  protected Elantris elantris;

  public Loop(Elantris elantris) {
    this.elantris = elantris;
    this.npcResolver = new NpcResolver();
  }

  public void tick(Action pcActions) {

    if (pcActions != null) {

      World world = elantris.world();
      PC pc = elantris.pc();

      List<Action> npcActions = new LinkedList<>();
      List<NPC> npcs = world.npcs();
      for (NPC npc : npcs) {
        Action action = npcResolver.resolve(npc);
        if (action != null) {
          npcActions.add(action);
        }
      }

      world.tick();
      pcActions.act();
      for (Action action : npcActions) {
        if (action != null)
          action.act();
      }

      if (pc != null && pc.complete() && pc.isDead()) {
        elantris.addMessage("You are dead!!!");
        pc.getSite().leave(pc);
        elantris.pc(null);
      }
    }

  }

}
