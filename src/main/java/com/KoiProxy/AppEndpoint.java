package com.KoiProxy;

public class AppEndpoint {
  static String host;
  static int port;

  static public void SetHost(String host) {
    AppEndpoint.host = host;
  }

  static public void SetPort(int port) {
    AppEndpoint.port = port;
  }
}
