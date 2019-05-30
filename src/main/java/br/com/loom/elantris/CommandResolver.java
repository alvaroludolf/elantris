package br.com.loom.elantris;

import java.util.LinkedList;
import java.util.List;

import br.com.loom.elantris.model.Action;
import br.com.loom.elantris.model.PC;

public class CommandResolver {

  public List<Action> resolve(PC actor, String cmd) {
    LinkedList<Action> actions = new LinkedList<>();
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
    }
    return actions;
  }

}
