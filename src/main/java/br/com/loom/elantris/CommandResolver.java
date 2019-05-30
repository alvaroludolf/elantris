package br.com.loom.elantris;

import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.Classe;
import br.com.loom.elantris.model.Direction;
import br.com.loom.elantris.model.PC;
import br.com.loom.elantris.model.Race;

public class CommandResolver {

  private Elantris elantris;

  public CommandResolver(Elantris elantris) {
    super();
    this.elantris = elantris;
  }

  public List<Action> resolve(PC actor, String cmd) {
    LinkedList<Action> actions = new LinkedList<>();
    if (actor != null && actor.complete()) {
      if ("a".equals(cmd)) {
        actions.add(() -> {
          actor.turnLeft();
          return true;
        });
      } else if ("d".equals(cmd)) {
        actions.add(() -> {
          actor.turnRight();
          return true;
        });
      } else if ("w".equals(cmd)) {
        actions.add(() -> {
          actor.moveForward();
          return true;
        });
      } else if ("s".equals(cmd)) {
        actions.add(() -> {
          actor.moveBackward();
          return true;
        });
      } else if ("q".equals(cmd)) {
        actions.add(() -> {
          actor.moveLeft();
          return true;
        });
      } else if ("e".equals(cmd)) {
        actions.add(() -> {
          actor.moveRight();
          return true;
        });
      } else {
        // NOOP
      }
    } else if (actor != null && !actor.complete()) {
      if (actor.completeness() == 0) {
        actor.setName(cmd);
        elantris.addMessage("What is your character class?");
        elantris.addMessage("1 - Warrior / 2 - Mage / 3 - Priest");
      } else if (actor.completeness() == 1) {
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
      } else if (actor.completeness() == 2) {
        if ("1".equals(cmd)) {
          actor.setRace(Race.HUMAN);
        } else if ("2".equals(cmd)) {
          actor.setRace(Race.ELF);
        } else if ("3".equals(cmd)) {
          actor.setRace(Race.DWARF);
        } else {
          return null;
        }
        actor.setHpMax(100);
        actor.setMpMax(100);
        actor.dropAt(elantris.world().site(500, 500), Direction.NORTH);
      }
    } else {
      if ("1".equals(cmd)) {
        elantris.pc();
        elantris.addMessage("What is your character name?");
        elantris.addMessage("");
      } else if ("2".equals(cmd)) {
        // load save file
      } else {
        // NOOP
      }
    }
    return actions;
  }

}
