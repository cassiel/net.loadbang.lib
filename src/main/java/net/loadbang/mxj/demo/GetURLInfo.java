// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

package net.loadbang.mxj.demo;

import java.net.*;
import java.io.*;
import java.util.Date;

/**
 * A class that displays information about a URL.
 **/
public class GetURLInfo {
  /** Use the URLConnection class to get info about the URL */
  public static void printinfo(URL url) throws IOException {
    URLConnection c = url.openConnection();  // Get URLConnection from the URL
    c.connect();                             // Open a connection to the URL

    // Display some information about the URL contents
    System.out.println("  Content Type: " + c.getContentType());
    System.out.println("  Content Encoding: " + c.getContentEncoding());
    System.out.println("  Content Length: " + c.getContentLength());
    System.out.println("  Date: " + new Date(c.getDate()));
    System.out.println("  Last Modified: " + new Date(c.getLastModified()));
    System.out.println("  Expiration: " + new Date(c.getExpiration()));

    // If it is an HTTP connection, display some additional information.
    if (c instanceof HttpURLConnection) {
      HttpURLConnection h = (HttpURLConnection) c;
      System.out.println("  Request Method: " + h.getRequestMethod());
      System.out.println("  Response Message: " + h.getResponseMessage());
      System.out.println("  Response Code: " + h.getResponseCode());
    }
  }
  
  /** Create a URL object, call printinfo() to display information about it. */
  public static void main(String[] args) {
    try { printinfo(new URL(args[0])); }
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java GetURLInfo <url>");
    }
  }
}
