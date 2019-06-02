package br.com.loom.elantris.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.loom.elantris.exceptions.ResourceNotFoundException;
import br.com.loom.elantris.helper.Log;

public class TextRepository {

  private static Map<String, String[]> resources = new HashMap<>();

  public static TextRepository instance = new TextRepository();

  public static TextRepository instance() {
    return instance;
  }

  public String[] readResource(String resourceName) {
    try {
      String[] resource = resources.get(resourceName);
      if (resource == null) {
        try (BufferedReader s = new BufferedReader(
            new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("br/com/loom/elantris/" + resourceName), StandardCharsets.UTF_8))) {
          List<String> resourceLines = new LinkedList<>();
          String line;
          while ((line = s.readLine()) != null)
            resourceLines.add(line);
          resource = resourceLines.toArray(new String[resourceLines.size()]);
          resources.put(resourceName, resource);
        }
      }
      return resource;
    } catch (IOException | NullPointerException e) {
      Log.log(e);
      throw new ResourceNotFoundException(e);
    }
  }

}
