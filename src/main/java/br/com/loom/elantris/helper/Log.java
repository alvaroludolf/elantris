package br.com.loom.elantris.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

public class Log {

  public static PrintWriter pw;

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

  public static void log(Throwable e) {
    log(e.getMessage());
    e.printStackTrace(pw);
    pw.flush();
  }

}
