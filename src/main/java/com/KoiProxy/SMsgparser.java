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

    if (packetName == "MSG") {
      try {
        result += "[MSG:" + packet.getDataInput().readUTF() + "]";
        result += "[TYPE:" + packet.getDataInput().readByte() + "]";
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "LOGIN_OPTION") {
      try {
        byte totalAcc = packet.getDataInput().readByte();
        for (int i = 0; i < totalAcc; ++i) {
          int id = packet.getDataInput().readInt();
          String name = packet.getDataInput().readUTF();
          byte a1 = packet.getDataInput().readByte();
          byte a2 = packet.getDataInput().readByte();
          byte a3 = packet.getDataInput().readByte();
          byte a4 = packet.getDataInput().readByte();

          result += "[ID:" + id + "]";
          result += "[NAME:" + name + "]";

          byte[] var13 = new byte[12];

          int var14;
          for (var14 = 0; var14 < var13.length; ++var14) {
            var13[var14] = -1;
          }

          byte var15;
          for (var14 = 0; var14 < a4; ++var14) {
            var15 = packet.getDataInput().readByte();
            byte var16 = packet.getDataInput().readByte();
            var13[var15] = var16;
          }

          short var19 = packet.getDataInput().readShort();
          var15 = packet.getDataInput().readByte();
          packet.getDataInput().readByte();
          packet.getDataInput().readByte();
          short var18;
          if ((var18 = packet.getDataInput().readShort()) >= 0) {
            String a5 = packet.getDataInput().readUTF();
            byte a6 = packet.getDataInput().readByte();
            result += "[CLAN:" + a5 + "]";
          }
          // todo
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "POPUP_MSG") {
      try {
        result += "[STR1:" + packet.getDataInput().readUTF() + "]";
        result += "[STR2:" + packet.getDataInput().readUTF() + "]";
        result += "[byte:" + packet.getDataInput().readByte() + "]";
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "DOING") {
      try {
        int length = packet.getDataInput().readShort();
        for (int i = 0; i < length; ++i) {
          short var3 = packet.getDataInput().readShort();
          String var4 = packet.getDataInput().readUTF();
          short var5 = (short) packet.getDataInput().readUnsignedByte();
          int var6 = packet.getDataInput().readInt();
          byte var7 = packet.getDataInput().readByte();

          result += var3 + "|";
          result += var4 + "|";
          result += var5 + "|";
          result += var6 + "|";
          result += var7 + "|";
          
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    return result;
  }
}
