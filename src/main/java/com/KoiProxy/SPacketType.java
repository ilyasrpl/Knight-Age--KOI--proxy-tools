package com.KoiProxy;

public class SPacketType {
  packetMsg packetmsgs[] = {
    new packetMsg((byte) -40, "INIT"),
    new packetMsg((byte) 53, "MSG"),
    new packetMsg((byte) 13, "LOGIN_OPTION"),
    new packetMsg((byte) 37, "POPUP_MSG"),
    new packetMsg((byte) 26, "DOING")
  };

  public String getPacketName(byte packetId) {
    for (packetMsg msg : packetmsgs) {
      if (msg.packetId == packetId) {
        return msg.name;
      }
    }
    return "undefined_" + packetId;
  }
}
