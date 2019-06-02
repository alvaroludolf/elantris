package br.com.loom.elantris;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Log {

  private static PrintWriter pw;

  static {
    try {
      pw = new PrintWriter("elantris.log");
    } catch (FileNotFoundException e) {
    }
  }

  public static void log(String s) {
    pw.println(s);
    pw.flush();
  }

}
