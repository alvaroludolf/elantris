package br.com.loom.elantris;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;

public class NpcActionResolver {

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
