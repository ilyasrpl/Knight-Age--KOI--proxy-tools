package com.KoiProxy;

public class CMsgparser {
  Packet packet;
  CMsgparser(Packet packet){
    this.packet = packet;
  }

  @Override
  public String toString() {
    String packetName = new CPacketType().getPacketName(packet.type);
    String result = "[" + packetName + "]";

    if(packetName == "INIT") {}

    if(packetName == "LOGIN") {
      try {
        result += "[EMAIL:" + packet.getDataInput().readUTF() + "]";
        result += "[PASSWORD:" + packet.getDataInput().readUTF() + "]";
        result += "[VERSION:" + packet.getDataInput().readUTF() + "]";
        // TODO
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if(packetName == "ACC_SELECT") {
      try {
        packet.getDataInput().readByte(); // always 0
        result += "[ACC_ID:" + packet.getDataInput().readInt() + "]";
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if(packetName == "MOVE") {
      try {
        result += "[X:" + packet.getDataInput().readShort() + "]";
        result += "[Y:" + packet.getDataInput().readShort() + "]";
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if(packetName == "SEND_CHAT") {
      try {
        result += "[MSG:" + packet.getDataInput().readUTF() + "]";
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    return result;
  }
}
