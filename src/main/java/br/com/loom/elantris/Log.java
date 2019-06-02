package br.com.loom.elantris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

public class Log {

  private static PrintWriter pw;

  static {
    try {
      pw = new PrintWriter(new File("elantris.log"));
    } catch (FileNotFoundException e) {
    }
  }

  public static void log(String s) {
    pw.println(new Date() + " - " + s);
    pw.flush();
  }

  public static PrintWriter printWriter() {
    return pw;
  }

}
