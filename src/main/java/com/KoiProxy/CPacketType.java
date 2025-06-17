package com.KoiProxy;

public class CPacketType {
  packetMsg packetmsgs[] = {
    new packetMsg((byte) -40, "INIT"),
    new packetMsg((byte) 1, "LOGIN"),
    new packetMsg((byte) 13, "ACC_SELECT"),
    new packetMsg((byte) 4, "MOVE"),
    new packetMsg((byte) 27, "SEND_CHAT")
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

class packetMsg {
  byte packetId;
  String name;

  public packetMsg(byte packetId, String name) {
    this.packetId = packetId;
    this.name = name;
  }
}
