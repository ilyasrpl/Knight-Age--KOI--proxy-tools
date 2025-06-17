package com.KoiProxy;

public class SMsgparser {
  Packet packet;

  SMsgparser(Packet packet) {
    this.packet = packet;
  }

  @Override
  public String toString() {
    String packetName = new SPacketType().getPacketName(packet.type);
    String result = "[" + packetName + "]";

    if (packetName == "INIT") {
      try {
        byte l = packet.getDataInput().readByte(); // keknya panjang enc, cuman gk tau si
        result += "[SET_KEY:" + Byte.toString(packet.getDataInput().readByte()) + "]";
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    return result;
  }
}
