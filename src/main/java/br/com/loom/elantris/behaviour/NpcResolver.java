package br.com.loom.elantris.behaviour;

import br.com.loom.elantris.helper.Log;
import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;

/**
 * Implements the AI of the npc. Nothing of interesting to see here for now.
 *
 */
public class NpcResolver {

  public Action resolve(NPC actor) {
    if (!actor.isDead() && actor.getSite() != null && actor.getSite().hasPc()) {
      PC pc = actor.getSite().getPc();
      Log.log(actor + " going to attacking " + pc + " " + actor.getSite().hasPc());
      return () -> {
        int damage = actor.attack(pc);
        Log.log(actor + " attacking " + pc + " for " + damage);
      };
    }
    return null;
  }

}
