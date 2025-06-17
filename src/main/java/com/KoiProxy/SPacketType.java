package com.KoiProxy;

public class SPacketType {
  packetMsg packetmsgs[] = {
    new packetMsg((byte) -40, "INIT")
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
