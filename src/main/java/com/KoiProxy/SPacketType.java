package com.KoiProxy;

public class SPacketType {
  packetMsg packetmsgs[] = {
    new packetMsg((byte) -40, "INIT"),
    new packetMsg((byte) 53, "MSG"),
    new packetMsg((byte) 13, "LOGIN_OPTION"),
    new packetMsg((byte) 37, "POPUP_MSG"),
    new packetMsg((byte) 26, "DOING"),
    new packetMsg((byte) 61, "DOING2"),
    new packetMsg((byte) 51, "CHANGE_ZONE"),
    new packetMsg((byte) 16, "UPDATE_INFO"),
    new packetMsg((byte) 30, "UPDATE_EXP"),
    new packetMsg((byte) 33, "UPDATE_LEVEL"),
    new packetMsg((byte) 12, "CHANGE_LOCATION"),
    new packetMsg((byte) 3, "DESC_SELF"),
    new packetMsg((byte) 32, "HEAL"),
    new packetMsg((byte) 10, "GOT_ATTACK"),
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
