package br.com.loom.elantris;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
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

  public Screen(PrintStream out, int height, int widith) throws IOException {
    this.out = new PrintWriter(out, true, StandardCharsets.UTF_8);
    this.mainScreen = readResource("br/com/loom/elantris/main.ans");
  }

  public void draw(World world, PC pc) {
    out.append("\u001b[10A");
    out.append("\u001b[0J");
    out.append(mainScreen);
  }

  private String readResource(String resourceName) throws IOException {
    System.out.println("Reading " + resourceName);
    String resource = resources.get(resourceName);
    if (resource == null) {
      Reader s = new InputStreamReader(this.getClass().getResourceAsStream(resource));
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
