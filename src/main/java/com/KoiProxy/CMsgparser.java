package com.KoiProxy;

public class CMsgparser {
  PlayerSession session;
  Packet packet;
  CMsgparser(Packet packet, PlayerSession session){
    this.packet = packet;
    this.session = session;
  }

  @Override
  public String toString() {
    String packetName = new CPacketType().getPacketName(packet.type);
    String result = "[" + packetName + "]";

    if(packetName == "INIT") {}

    if(packetName == "LOGIN") {
      try {
        String email = packet.getDataInput().readUTF();
        String password = packet.getDataInput().readUTF();
        result += "[EMAIL:" + email + "]";
        result += "[PASSWORD:" + password + "]";
        result += "[VERSION:" + packet.getDataInput().readUTF() + "]";
        // TODO
        session.setEmail(email);
        session.setPassword(password);
        // TODO
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if(packetName == "ACC_SELECT") {
      try {
        packet.getDataInput().readByte(); // always 0
        int id = packet.getDataInput().readInt();
        result += "[ACC_ID:" + id + "]";
        CharOpt charopt = CharOpt.findById(session.CharOpt, id);
        session.setName(charopt.name);
        session.setLevel(charopt.level);
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
