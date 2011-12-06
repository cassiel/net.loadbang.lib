package net.loadbang.mxj.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import net.loadbang.mxj.demo.Server.Service;
import net.loadbang.mxj.demo.Server.Logger;

/**
 * A very simple service.  It displays the current time on the server
 * to the client, and closes the connection.
 **/
public class Time implements Service {
  public void serve(InputStream i, OutputStream o, Logger logger) throws IOException {
    PrintWriter out = new PrintWriter(new OutputStreamWriter(o));
    Date d = new Date();
    out.println(d);
    logger.log(d.toString());
    out.close();
    i.close();
  }
}
