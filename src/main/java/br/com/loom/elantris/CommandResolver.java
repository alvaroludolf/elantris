package br.com.loom.elantris;

import java.io.IOException;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.character.Classe;
import br.com.loom.elantris.model.character.Direction;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.character.Persona;
import br.com.loom.elantris.model.character.Race;

public class CommandResolver {

  private Elantris elantris;

  public CommandResolver(Elantris elantris) {
    super();
    this.elantris = elantris;
  }

  public Action resolve(PC actor, String cmd) throws IOException {
    if (actor != null && actor.complete() && !actor.isDead()) {
      NPC npc = actor.getSite().getNpc();
      if ("a".equals(cmd)) {
        return () -> {
          actor.turnLeft();
          return true;
        };
      } else if ("d".equals(cmd)) {
        return () -> {
          actor.turnRight();
          return true;
        };
      } else if ("w".equals(cmd)) {
        return () -> {
          actor.moveForward();
          return true;
        };
      } else if ("s".equals(cmd)) {
        return () -> {
          actor.moveBackward();
          return true;
        };
      } else if ("q".equals(cmd)) {
        return () -> {
          actor.moveLeft();
          return true;
        };
      } else if ("e".equals(cmd)) {
        return () -> {
          actor.moveRight();
          return true;
        };
      } else if ("1".equals(cmd)) {
        return () -> {
          actor.attack((Persona) npc);
          return true;
        };
      } else if ("2".equals(cmd)) {
        return () -> {
          actor.cast((Persona) npc);
          return true;
        };
      } else if ("3".equals(cmd)) {
        return () -> {
          actor.heal((Persona) actor);
          return true;
        };
      } else {
        // NOOP
      }
    } else if (actor != null && !actor.complete()) {
      if (actor.getName() == null) {
        actor.setName(cmd);
        elantris.addMessage("What is your character class?");
        elantris.addMessage("1 - Warrior / 2 - Mage / 3 - Priest");
      } else if (actor.getClasse() == null) {
        if ("1".equals(cmd)) {
          actor.setClasse(Classe.WARRIOR);
        } else if ("2".equals(cmd)) {
          actor.setClasse(Classe.MAGE);
        } else if ("3".equals(cmd)) {
          actor.setClasse(Classe.PRIEST);
        } else {
          return null;
        }
        elantris.addMessage("What is your character race?");
        elantris.addMessage("1 - Human / 2 - Elf / 3 - Dwarf");
      } else if (actor.getRace() == null) {
        if ("1".equals(cmd)) {
          actor.setRace(Race.HUMAN);
        } else if ("2".equals(cmd)) {
          actor.setRace(Race.ELF);
        } else if ("3".equals(cmd)) {
          actor.setRace(Race.DWARF);
        } else {
          return null;
        }
        actor.setHpMax(actor.getRace().maxHp() + actor.getClasse().maxHp());
        actor.setMpMax(actor.getRace().maxMp() + actor.getClasse().maxMp());
        actor.dropAt(elantris.world().site(500, 500), Direction.NORTH);
        elantris.setHelp(TextResourceManager.instance().readResource("playHelp.ans"));
        elantris.save();
      }
    } else {
      if ("1".equals(cmd)) {
        elantris.pc();
        elantris.addMessage("What is your character name?");
        elantris.setHelp("");
      } else if ("2".equals(cmd)) {
        elantris.load();
        elantris.setHelp(TextResourceManager.instance().readResource("playHelp.ans"));
      } else {
        // NOOP
      }
    }
    return null;
  }

}
