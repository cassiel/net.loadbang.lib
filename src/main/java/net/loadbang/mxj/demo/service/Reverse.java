package net.loadbang.mxj.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import net.loadbang.mxj.demo.Server.Service;
import net.loadbang.mxj.demo.Server.Logger;

/**
 * A very simple service.  It displays the current time on the server
 * to the client, and closes the connection.
 **/
public class Reverse implements Service {
    public void serve(InputStream i, OutputStream o, Logger logger) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(i));
        PrintWriter out = 
          new PrintWriter(new BufferedWriter(new OutputStreamWriter(o)));
        out.println("Welcome to the line reversal server.");
        out.println("Enter lines.  End with a '.' on a line by itself");
        for(;;) {
          out.print("> ");
          out.flush();
          String line = in.readLine();
          if ((line == null) || line.equals(".")) break;
          logger.log(line);
          for(int j = line.length()-1; j >= 0; j--)
            out.print(line.charAt(j));
          out.println();
        }
        out.close();
        in.close();
      }
}
