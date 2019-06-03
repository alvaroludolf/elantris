package br.com.loom.elantris.model.character;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.loom.elantris.model.MonsterSpec;

public class PCTest {

  @Test
  public void testAttack() {
    PC pc = new PC();
    pc.setClasse(Classe.WARRIOR);
    pc.setRace(Race.HUMAN);
    pc.setHpMax(100);
    pc.setHp(100);
    pc.setMpMax(100);
    pc.setMp(100);

    MonsterSpec spec = new MonsterSpec("Kobold;500;10;0;50;5");
    NPC npc = new NPC(spec);
    npc.setHpMax(100);
    npc.setHp(100);

    assertTrue(npc.health() == 4);
    pc.attack(npc);
    assertTrue(npc.health() < 4);

  }

  @Test
  public void testCast() {
    PC pc = new PC();
    pc.setClasse(Classe.MAGE);
    pc.setRace(Race.HUMAN);
    pc.setHpMax(100);
    pc.setHp(100);
    pc.setMpMax(100);
    pc.setMp(100);

    MonsterSpec spec = new MonsterSpec("Kobold;500;10;0;50;5");
    NPC npc = new NPC(spec);
    npc.setHpMax(100);
    npc.setHp(100);

    assertTrue(npc.health() == 4);
    pc.cast(npc);
    assertTrue(npc.health() < 4);

  }

  @Test
  public void testHeal() {
    PC pc = new PC();
    pc.setClasse(Classe.PRIEST);
    pc.setRace(Race.HUMAN);
    pc.setHpMax(100);
    pc.setHp(50);
    pc.setMpMax(100);
    pc.setMp(100);

    pc.heal(pc);
    assertTrue(pc.getHp() > 50);

  }

  @Test
  public void testRest() {
    PC pc = new PC();
    pc.setClasse(Classe.PRIEST);
    pc.setRace(Race.HUMAN);
    pc.setHpMax(100);
    pc.setHp(20);
    pc.setMpMax(100);
    pc.setMp(50);

    int restTime = pc.rest();
    assertTrue(pc.getHp() == pc.getHpMax());
    assertEquals(80, restTime);

  }

}
