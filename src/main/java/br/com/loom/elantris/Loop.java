package br.com.loom.elantris;

import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;

public class Loop {

  private NpcActionResolver resolver = new NpcActionResolver();
  private Elantris elantris;

  public Loop(Elantris elantris) {
    this.elantris = elantris;
  }

  public void tick(Action pcActions) {

    World world = elantris.world();
    PC pc = elantris.pc();

    if (pcActions != null) {

      List<Action> npcActions = new LinkedList<>();
      List<NPC> npcs = world.getNpcs();
      for (NPC npc : npcs) {
        Action action = resolver.resolve(npc);
        if (action != null) {
          npcActions.add(action);
        }
      }

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
    if (pc == null || (pc.complete() && pc.isDead())) {
      elantris.setHelp(TextResourceManager.instance().readResource("createHelp.ans"));
    } else if (pc != null && pc.complete() && !pc.isDead()) {
      elantris.setHelp(TextResourceManager.instance().readResource("playHelp.ans"));
    } else {
      elantris.setHelp("");
    }

    world.tick();

  }

}
