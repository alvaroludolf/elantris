package br.com.loom.elantris;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import br.com.loom.elantris.model.Direction;
import br.com.loom.elantris.model.PC;
import br.com.loom.elantris.model.World;

public class Screen {

  private PrintWriter out;
  private int height;
  private int widith;
  private Map<String, String> resources = new HashMap<>();
  private String mainScreen;

  public Screen(PrintStream out, int height, int widith) throws IOException, URISyntaxException {
    this.out = new PrintWriter(out, true, StandardCharsets.UTF_8);
    this.mainScreen = readResource("main.ans");
    this.height = height;
    this.widith = widith;
  }

  public void draw(World world, PC pc) {
    clearScreen();
    out.print(mainScreen);
    resetCursor();
    for (int y = 0; y < height; y++) {
      breakLine();
      for (int x = 0; x < widith; x++) {
        if (x == 68 && y == 10) {
          out.print("@");
        } else if (x == 68 && y == 0 && pc.getDirection() == Direction.NORTH) {
          out.print("N");
        } else if (x == 68 && y == 12 && pc.getDirection() == Direction.SOUTH) {
          out.print("N");
        } else if (x == 57 && y == 6 && pc.getDirection() == Direction.EAST) {
          out.print("N");
        } else if (x == 79 && y == 6 && pc.getDirection() == Direction.WEST) {
          out.print("N");
        } else if (x > 57 && x < 79 && y > 0 && y < 11) {
          out.print("░");
        } else {
          moveRight(1);
        }
      }
    }
    DecimalFormat df = new DecimalFormat("00000");
    resetCursor();
    moveDown(12);
    moveRight(62);
    out.print(df.format(pc.getSite().getLon()));
    moveLeft(widith);
    moveRight(73);
    out.print(df.format(pc.getSite().getLat()));

    conclude();
    out.flush();
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
    moveLeft(widith);
  }

  protected void resetCursor() {
    moveUp(height - 1);
    moveLeft(widith);
  }

  protected void conclude() {
    moveDown(height - 1);
    moveLeft(widith);
    moveUp(1);
    moveRight(2);
  }

  private String readResource(String resourceName) throws IOException, URISyntaxException {
    String resource = resources.get(resourceName);
    if (resource == null) {
      Reader s = new InputStreamReader(Screen.class.getResourceAsStream(resourceName), StandardCharsets.UTF_8);
      Writer w = new StringWriter();
      copy(s, w);
      resource = w.toString();
      resources.put(resourceName, resource);
    }
    return resource;
  }

  private void copy(Reader s, Writer w) throws IOException {
    try (s; w) {
      char[] cbuf = new char[8192];
      int len;
      while ((len = s.read(cbuf)) >= 0) {
        w.write(cbuf, 0, len);
      }
    }
  }

}
