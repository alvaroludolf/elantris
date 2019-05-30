package br.com.loom.elantris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.loom.elantris.model.Direction;
import br.com.loom.elantris.model.PC;
import br.com.loom.elantris.model.Site;
import br.com.loom.elantris.model.World;

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

  private PrintWriter out;
  private int height;
  private int width;
  private Map<String, String[]> resources = new HashMap<>();
  private String[] mainScreen;
  private String[] body;
  private String[] message;
  private String[] help;

  public Screen(PrintStream out, int height, int width) throws IOException, URISyntaxException {
    this.out = new PrintWriter(out, true, StandardCharsets.UTF_8);
    this.mainScreen = readResource("main.ans");
    this.body = readResource("initial.ans");
    this.message = new String[2];
    this.help = new String[] { "1 - Create a new player", "2 - Load saved game" };
    this.height = height;
    this.width = width;
  }

  public void draw(World world, PC pc) {
    resetCursor();
    for (int y = 0; y < height; y++) {
      breakLine();
      out.print(mainScreen[y]);
      moveLeft(width);
      for (int x = 0; x < width; x++) {
        if (x > 1 && x < 56 && y > 0 && y < 21) {
          if (y - 1 < body.length && body[y - 1] != null && x - 2 < body[y - 1].length())
            colorChar(body[y - 1].charAt(x - 2), WHITE);
          else
            moveRight(1);
        } else if (x > 1 && x < 79 && y == 22) {
          writeLine(x, message[0]);
        } else if (x > 1 && x < 79 && y == 23) {
          writeLine(x, message[1]);
        } else if (x > 1 && x < 79 && y == 25) {
          writeLine(x, help[0]);
        } else if (x > 1 && x < 79 && y == 26) {
          writeLine(x, help[1]);
        } else if (pc != null && world != null) {
          if (!minimap(world, pc, y, x))
            moveRight(1);
        }
      }
    }
    if (pc != null && world != null) {
      coordinates(pc);
    }
    if (pc == null) {
      resetCursor();
      for (int y = 0; y < height; y++) {

      }
    }

    conclude();
    out.flush();
  }

  protected void writeLine(int x, String s) {
    if (s != null && x - 2 < s.length())
      colorChar(s.charAt(x - 2), WHITE);
    else
      moveRight(1);
  }

  protected void coordinates(PC pc) {
    DecimalFormat df = new DecimalFormat("00000");
    resetCursor();
    moveDown(12);
    moveRight(62);
    out.print(df.format(pc.getSite().getLon()));
    moveLeft(width);
    moveRight(73);
    out.print(df.format(pc.getSite().getLat()));
  }

  protected boolean minimap(World world, PC pc, int y, int x) {
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
      if (site.getLat() == 500 && site.getLon() == 500) {
        colorChar('▒', BRIGHT_WHITE);
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
    } else {
      return false;
    }
    return true;
  }

  protected void colorChar(char c, String color) {
    out.print(color + c);
    colorReset();
  }

  protected void colorReset() {
    out.print(RESET);
  }

  protected void moveUp(int n) {
    out.print("\u001b[" + n + "A");
  }

  protected void moveDown(int n) {
    out.print("\u001b[" + n + "B");
  }

  protected void moveRight(int n) {
    out.print("\u001b[" + n + "C");
  }

  protected void moveLeft(int n) {
    out.print("\u001b[" + n + "D");
  }

  protected void clearScreen() {
    out.print("\u001b[0J");
  }

  protected void breakLine() {
    moveDown(1);
    moveLeft(width);
  }

  protected void resetCursor() {
    moveUp(height - 1);
    moveLeft(width);
  }

  protected void conclude() {
    moveDown(height - 1);
    moveLeft(width);
    moveUp(1);
    moveRight(2);
  }

  private String[] readResource(String resourceName) throws IOException {
    String[] resource = resources.get(resourceName);
    if (resource == null) {
      try (BufferedReader s = new BufferedReader(
          new InputStreamReader(Screen.class.getResourceAsStream(resourceName), StandardCharsets.UTF_8))) {
        List<String> resourceLines = new LinkedList<>();
        String line;
        while ((line = s.readLine()) != null)
          resourceLines.add(line);
        resource = resourceLines.toArray(new String[resourceLines.size()]);
        resources.put(resourceName, resource);
      }
    }
    return resource;
  }

}
