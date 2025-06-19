package com.KoiProxy;

public class SPacketType {
  packetMsg packetmsgs[] = {
    new packetMsg((byte) -40, "INIT"),
    new packetMsg((byte) 53, "MSG"),
    new packetMsg((byte) 13, "LOGIN_OPTION"),
    new packetMsg((byte) 37, "POPUP_MSG"),
    new packetMsg((byte) 26, "DOING"),
    new packetMsg((byte) 16, "UPDATE_INFO"),
    new packetMsg((byte) 30, "UPDATE_EXP"),
    new packetMsg((byte) 33, "UPDATE_LEVEL")
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
