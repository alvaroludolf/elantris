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
import java.util.HashMap;
import java.util.Map;

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
        if (x > 57 && x < 79 && y > 0 && y < 11) {
          out.print("░");
        } else {
          moveRight();
        }
      }
    }

    out.print("\n");
    out.flush();
  }

  protected void moveRight() {
    out.print("\u001b[1C");
  }

  protected void breakLine() {
    out.print("\u001b[1B\u001b[" + widith + "D");
  }

  protected void resetCursor() {
    out.print("\u001b[" + (height - 1) + "A");
    out.print("\u001b[" + widith + "D");
  }

  protected void clearScreen() {
    out.print("\u001b[0J");
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
