package br.com.loom.elantris.behaviour;

import java.io.IOException;

import br.com.loom.elantris.Elantris;
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
    if ("quit".equals(cmd) || "exit".equals(cmd))
      System.exit(0);
    if (actor != null && actor.complete() && !actor.isDead()) {
      NPC npc = actor.getSite().getNpc();
      if ("a".equals(cmd)) {
        return () -> {
          actor.turnLeft();
        };
      } else if ("d".equals(cmd)) {
        return () -> {
          actor.turnRight();
        };
      } else if ("w".equals(cmd)) {
        return () -> {
          actor.moveForward();
        };
      } else if ("s".equals(cmd)) {
        return () -> {
          actor.moveBackward();
        };
      } else if ("q".equals(cmd)) {
        return () -> {
          actor.moveLeft();
        };
      } else if ("e".equals(cmd)) {
        return () -> {
          actor.moveRight();
        };
      } else if ("1".equals(cmd)) {
        if (!npc.isDead())
          return () -> {
            actor.attack((Persona) npc);
          };
      } else if ("2".equals(cmd)) {
        if (!npc.isDead())
          return () -> {
            actor.cast((Persona) npc);
          };
      } else if ("3".equals(cmd)) {
        if (!npc.isDead())
          return () -> {
            actor.heal((Persona) actor);
          };
      } else if ("r".equals(cmd)) {
        if (npc == null || npc.isDead())
          return () -> {
            int timeToRest = actor.rest();
            for (int i = 0; i < timeToRest; i++) {
              elantris.world().tick();
            }
          };
      } else if ("t".equals(cmd)) {
        elantris.save();
      } else if ("y".equals(cmd)) {
        elantris.load();
      } else if ("u".equals(cmd)) {
        elantris.restart();
      } else {
        // NOOP
      }
    } else if (actor != null && !actor.complete()) { // Complete the character creation process.
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
      }
    } else {
      if ("1".equals(cmd)) { // creates a new character
        elantris.pc();
        elantris.addMessage("What is your character name?");
      } else if ("2".equals(cmd)) { // load a save game
        elantris.load();
      } else {
        // NOOP
      }
    }
    return null;
  }

}
