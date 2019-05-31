package br.com.loom.elantris;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import br.com.loom.elantris.model.World;
import br.com.loom.elantris.model.character.PC;

public class SaveRepository {

  public void save(Elantris elantris) throws IOException {
    File p = new File("save");
    if (!p.exists())
      p.mkdirs();
    File f = new File(p, "savegame.sav");
    if (f.exists()) {
      try (FileOutputStream out = new FileOutputStream(new File(p, "savegame.bak"))) {
        Files.copy(f.toPath(), out);
      }
    }
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, false))) {
      oos.writeObject(elantris.pc());
      oos.writeObject(elantris.world());
    }
  }

  public void load(Elantris elantris) throws IOException {
    File f = new File("save", "savegame.sav");
    if (f.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
        try {
          elantris.pc((PC) ois.readObject());
          elantris.world((World) ois.readObject());
        } catch (ClassNotFoundException e) {
          throw new IOException(e);
        }
      }
    }
  }
}
