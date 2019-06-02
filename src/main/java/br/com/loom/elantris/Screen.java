package br.com.loom.elantris;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.Direction;
import br.com.loom.elantris.model.character.NPC;
import br.com.loom.elantris.model.character.PC;
import br.com.loom.elantris.model.site.Site;

public class Screen {

  public static final String BRIGHT_BLACK = "\u001b[30;1m";
  public static final String BRIGHT_RED = "\u001b[31;1m";
  public static final String BRIGHT_GREEN = "\u001b[32;1m";
  public static final String BRIGHT_YELLOW = "\u001b[33;1m";
  public static final String BRIGHT_BLUE = "\u001b[34;1m";
  public static final String BRIGHT_MAGENTA = "\u001b[35;1m";
  public static final String BRIGHT_CYAN = "\u001b[36;1m";
  public static final String BRIGHT_WHITE = "\u001b[37;1m";
  public static final String BLACK = "\u001b[30m";
  public static final String RED = "\u001b[31m";
  public static final String GREEN = "\u001b[32m";
  public static final String YELLOW = "\u001b[33m";
  public static final String BLUE = "\u001b[34m";
  public static final String MAGENTA = "\u001b[35m";
  public static final String CYAN = "\u001b[36m";
  public static final String WHITE = "\u001b[37m";
  public static final String RESET = "\u001b[0m";
  public static final String[] logo = TextResourceManager.instance().readResource("logo.ans");
  public static final String[] mainScreen = TextResourceManager.instance().readResource("main.ans");

  private PrintWriter out;
  private int height;
  private int width;
  private String[] body = new String[13];
  private String[] message = new String[3];
  private String[] help = new String[3];

  public Screen(PrintStream out, int height, int width) {
    this.out = new PrintWriter(out, true, StandardCharsets.UTF_8);
    setHelp(TextResourceManager.instance().readResource("createHelp.ans"));
    this.height = height;
    this.width = width;
  }

  public void addMessage(String m) {
    message[0] = message[1];
    message[1] = message[2];
    message[2] = m;
  }

  public void draw(World world, PC pc) {
    DecimalFormat df = new DecimalFormat("00000");

    resetCursor();
    for (int y = 0; y < height; y++) {
      breakLine();
      out.print(mainScreen[y]);
      moveLeft(width);

      buildBody(pc);

      for (int x = 0; x < width; x++) {
        if (writeAt(x, y, 1, 56, 1, 5, logo)) {
        } else if (writeAt(x, y, 1, 55, 7, 18, body)) {
        } else if (writeAt(x, y, 1, 78, 21, 23, message)) {
        } else if (writeAt(x, y, 1, 78, 25, 27, help)) {
        } else if (pc != null && writeAt(x, y, 63, 78, 13, pc.getName())) {
        } else if (pc != null && pc.getRace() != null && writeAt(x, y, 63, 74, 14, pc.getRace().name())) {
        } else if (pc != null && pc.getClasse() != null && writeAt(x, y, 64, 78, 15, pc.getClasse().name())) {
        } else if (pc != null && pc.complete() && writeAt(x, y, 61, 78, 16, pc.getHp() + "/" + pc.getHpMax())) {
        } else if (pc != null && pc.complete() && writeAt(x, y, 61, 78, 17, pc.getMp() + "/" + pc.getMpMax())) {
        } else if (pc != null && pc.complete() && writeAt(x, y, 61, 78, 18, String.valueOf(pc.getXp()))) {
        } else if (pc != null && pc.complete() && writeAt(x, y, 64, 78, 19, String.valueOf(world.getTime()))) {
        } else if (pc != null && pc.complete() && writeAt(x, y, 61, 67, 11, df.format(pc.getSite().getLon()))) {
        } else if (pc != null && pc.complete() && writeAt(x, y, 72, 78, 11, df.format(pc.getSite().getLat()))) {
        } else if (minimap(world, pc, y, x)) {
        } else {
          moveRight(1);
        }
      }
    }

    conclude();
    out.flush();
  }

  public String[] getHelp() {
    return help;
  }

  public String[] getMessage() {
    return message;
  }

  public void setHelp(String... h) {
    help[0] = (h.length > 0) ? h[0] : "";
    help[1] = (h.length > 1) ? h[1] : "";
    help[2] = (h.length > 2) ? h[2] : "";
  }

  protected void breakLine() {
    moveDown(1);
    moveLeft(width);
  }

  protected void buildBody(PC pc) {
    for (int i = 0; i < body.length; i++) {
      body[i] = null;
    }
    if (pc == null || !pc.complete()) {
      for (int i = 0; i < body.length; i++) {
        body[i] = TextResourceManager.instance().readResource("initial.ans")[i];
      }
      return;
    }
    int i = 0;
    Site site = pc.getSite();
    body[i++] = "You are in a " + site.getType().shortDescription();
    String[] descriptions = pc.getSite().getType().longDescription();
    for (String description : descriptions) {
      body[i++] = description;
    }
    i++;
    if (site.hasNpc()) {
      NPC npc = site.getNpc();
      switch (npc.health()) {
      case 4:
        body[i++] = "In front of you, you see a " + npc.getName() + ". IT ATTACKS!!!";
        break;
      case 3:
        body[i++] = "In front of you, you see a " + npc.getName() + ". It is bruised.";
        break;
      case 2:
        body[i++] = "In front of you, you see a " + npc.getName() + ". It is hurt.";
        break;
      case 1:
        body[i++] = "In front of you, you see a " + npc.getName() + ". It is injuried.";
        break;
      case 0:
        if (npc.isDead()) {
          body[i++] = "In front of you, you see a " + npc.getName() + ". It is dead...";
        } else {
          body[i++] = "In front of you, you see a " + npc.getName() + ". it is crippled.";
        }
      }

    }
  }

  protected void clearScreen() {
    out.print("\u001b[0J");
  }

  protected void colorChar(char c, String color) {
    out.print(color + c);
    colorReset();
  }

  protected void colorReset() {
    out.print(RESET);
  }

  protected void conclude() {
    moveDown(height - 1);
    moveLeft(width);
    moveUp(1);
    moveRight(2);
  }

  protected boolean minimap(World world, PC pc, int y, int x) {
    if (pc == null || !pc.complete() || world == null) {
      return false;
    }
    if (x == 68 && y == 10) {
      colorChar('@', BRIGHT_YELLOW);
    } else if (x == 68 && y == 0 && pc.getDirection() == Direction.NORTH) {
      out.print("N");
    } else if (x == 68 && y == 12 && pc.getDirection() == Direction.SOUTH) {
      out.print("N");
    } else if (x == 57 && y == 6 && pc.getDirection() == Direction.EAST) {
      out.print("N");
    } else if (x == 79 && y == 6 && pc.getDirection() == Direction.WEST) {
      out.print("N");
    } else if (x > 57 && x < 79 && y > 0 && y < 11) {
      int forwardDelta = 10 - y;
      int sidewayDelta = x - 68;
      Site site = null;
      switch (pc.getDirection()) {
      case EAST:
        site = world.site(pc.getSite().getLat() - sidewayDelta, pc.getSite().getLon() + forwardDelta);
        break;
      case NORTH:
        site = world.site(pc.getSite().getLat() + forwardDelta, pc.getSite().getLon() + sidewayDelta);
        break;
      case SOUTH:
        site = world.site(pc.getSite().getLat() - forwardDelta, pc.getSite().getLon() - sidewayDelta);
        break;
      case WEST:
        site = world.site(pc.getSite().getLat() + sidewayDelta, pc.getSite().getLon() - forwardDelta);
        break;
      }

      int max = Math.max(Math.abs(sidewayDelta), Math.abs(forwardDelta));
      int min = Math.min(Math.abs(sidewayDelta), Math.abs(forwardDelta));
      int distance = (int) ((max - min) + (min * 1.4142));

      if (site.getLat() == 500 && site.getLon() == 500) {
        colorChar('▒', BRIGHT_WHITE);
      } else if (site.hasNpc() && distance < 6) {
        if (site.getNpc().isDead()) {
          colorChar('w', RED);
        } else {
          colorChar('M', BRIGHT_RED);
        }
      } else {
        switch (site.getType()) {
        case FOREST:
          colorChar('♣', GREEN);
          break;
        case HILL:
          colorChar('∩', BRIGHT_GREEN);
          break;
        case MARCH:
          colorChar('ⁿ', GREEN);
          break;
        case MOUNTAIN:
          colorChar('▲', RED);
          break;
        case PLAIN:
          colorChar('.', BRIGHT_GREEN);
          break;
        }
      }
    } else

    {
      return false;
    }
    return true;
  }

  protected void moveDown(int n) {
    out.print("\u001b[" + n + "B");
  }

  protected void moveLeft(int n) {
    out.print("\u001b[" + n + "D");
  }

  protected void moveRight(int n) {
    out.print("\u001b[" + n + "C");
  }

  protected void moveUp(int n) {
    out.print("\u001b[" + n + "A");
  }

  protected void resetCursor() {
    moveUp(height - 1);
    moveLeft(width);
  }

  protected boolean writeAt(int x, int y, int x1, int x2, int y1, int y2, String... s) {
    int i = y - y1;
    if (i >= 0 && i < s.length && y >= y1 && y <= y2 && writeAt(x, y, x1, x2, y, s[i])) {
      return true;
    }
    return false;
  }

  protected boolean writeAt(int x, int y, int x1, int x2, int y1, String s) {
    if (x > x1 && x <= x2 && y == y1) {
      writeCharacter(x - x1 - 1, s);
      return true;
    }
    return false;
  }

  protected void writeCharacter(int i, String s) {
    if (s != null && i < s.length())
      colorChar(s.charAt(i), WHITE);
    else
      moveRight(1);
  }

}
