package com.KoiProxy;

public class SMsgparser {
  Packet packet;
  PlayerSession session;

  SMsgparser(Packet packet, PlayerSession session) {
    this.packet = packet;
    this.session = session;
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

    if (packetName == "CHANGE_ZONE") {
      try {
        byte zone = packet.getDataInput().readByte();
        short x = packet.getDataInput().readByte();
        short y = packet.getDataInput().readByte();
        result += "[ZONE:" + zone + 1 + "]";
        result += "[POS_X:" + x + "]";
        result += "[POS_Y:" + y + 1 + "]";
        session.setZone(zone);
        session.setPos(new Pos(x, y));
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

    if (packetName == "UPDATE_EXP") {
      try {
        short a1 = packet.getDataInput().readShort();
        short exp = packet.getDataInput().readShort();
        int a3 = packet.getDataInput().readInt();
        result += "[A1:" + a1 + "]";
        result += "[EXP_PERCENT:" + exp + "]";
        result += "[EXP_PLUS:" + a3 + "]";
        session.setExpPercent(exp);
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "UPDATE_LEVEL") {
      try {
        short a1 = packet.getDataInput().readShort();
        byte level = packet.getDataInput().readByte();
        result += "[A1:" + a1 + "]";
        result += "[LEVEL:" + level + "]";
        session.setLevel(level);
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

          short level = packet.getDataInput().readShort();
          result += "[LEVEL:" + level + "]";
          var15 = packet.getDataInput().readByte();
          packet.getDataInput().readByte();
          packet.getDataInput().readByte();
          short var18;
          if ((var18 = packet.getDataInput().readShort()) >= 0) {
            String a5 = packet.getDataInput().readUTF();
            byte a6 = packet.getDataInput().readByte();
            result += "[CLAN:" + a5 + "]";
          }

          session.CharOpt[i] = new CharOpt(id, name, level);

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

    if (packetName == "DOING2") {
      try {
        int length = packet.getDataInput().readUnsignedByte();
        String[] mapNames = new String[length];
        for (int i = 0; i < length; ++i) {
          mapNames[i] = packet.getDataInput().readUTF();
          result += mapNames[i] += "|";
        }
        session.mapNames = mapNames;
        // todo
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "UPDATE_INFO") {
      try {
        byte a1 = packet.getDataInput().readByte();
        byte a2 = packet.getDataInput().readByte();
        result += "[A1:" + a1 + "]";
        result += "[A2:" + a2 + "]";
        long gold = packet.getDataInput().readLong();
        long gems = (long) packet.getDataInput().readInt();
        result += "[GOLD:" + gold + "]";
        result += "[GEMS:" + gems + "]";
        session.setGold(gold);
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "CHANGE_LOCATION") {
      try {
        short mapIndex = packet.getDataInput().readShort();
        session.locationNow = PlayerSession.mapNames[mapIndex];
        short posX = packet.getDataInput().readShort();
        short posY = packet.getDataInput().readShort();

        packet.getDataInput().readShort();
        packet.getDataInput().readUTF();

        short var2 = packet.getDataInput().readShort();
        byte[] var3 = null;
        if (var2 > 0) {
          var3 = new byte[var2];
          packet.getDataInput().read(var3);
        }
        byte var8;
        short var9;
        if ((var8 = packet.getDataInput().readByte()) >= 0) {
          var9 = packet.getDataInput().readShort();
        }
        var9 = packet.getDataInput().readShort();
        if (var9 > 0) {
          byte[] a = new byte[var9];
          packet.getDataInput().read(a);
        }

        var8 = packet.getDataInput().readByte();
        for (int var10 = 0; var10 < var8; ++var10) {
          packet.getDataInput().readShort();
          packet.getDataInput().readShort();
          packet.getDataInput().readUTF();
        }

        packet.getDataInput().readByte();
        byte zone = packet.getDataInput().readByte();
        session.setZone(zone);
        // todo
        result += "[TODO]";

      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "DESC_SELF") {
      try {
        short idSessionInGame = packet.getDataInput().readShort();
        String name = packet.getDataInput().readUTF().toLowerCase();
        int hpNow = packet.getDataInput().readInt();
        int hpMax = packet.getDataInput().readInt();
        int mpNow = packet.getDataInput().readInt();
        int maxMp = packet.getDataInput().readInt();
        byte a7 = packet.getDataInput().readByte();
        byte a8 = packet.getDataInput().readByte();
        byte a9 = packet.getDataInput().readByte();
        byte a10 = packet.getDataInput().readByte();
        result += "[ID_SESSION_IN_GAME:" + idSessionInGame + "]";
        result += "[NAME:" + name + "]";
        result += "[HP_NOW:" + hpNow + "]";
        result += "[HP_MAX:" + hpMax + "]";
        result += "[MP_NOW:" + mpNow + "]";
        result += "[MP_MAX:" + maxMp + "]";
        result += "[A7:" + a7 + "]";
        result += "[A8:" + a8 + "]";
        result += "[A9:" + a9 + "]";
        result += "[A10:" + a10 + "]";

        session.idSessionInGame = idSessionInGame;
        session.name = name;
        session.hpNow = hpNow;
        session.hpMax = hpMax;
        // todo
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "HEAL") {
      try {
        byte a1 = packet.getDataInput().readByte();
        short idPlayer = packet.getDataInput().readShort();
        if(idPlayer != session.idSessionInGame) return "";
        short a3 = packet.getDataInput().readShort(); // is me ?
        result += "[A1:" + a1 + "]";
        result += "[A2:" + idPlayer + "]";
        result += "[A3:" + a3 + "]";
        byte var4 = packet.getDataInput().readByte();
        if (var4 == 0) {
          int hpMax = packet.getDataInput().readInt();
          int hpNow = packet.getDataInput().readInt();
          int heal = packet.getDataInput().readInt();
          result += "[HP_NOW:" + hpNow + "]";
          result += "[HP_HEAL:" + heal + "]";
          result += "[HP_MAX:" + hpMax + "]";
          session.hpNow = hpNow;
          session.hpMax = hpMax;
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    if (packetName == "GOT_ATTACK") {
      try {
        byte a1 = packet.getDataInput().readByte();
        short a2 = packet.getDataInput().readShort();
        result += "[A2:" + a2 + "]";
        if (a1 == 1) {
          int a3 = packet.getDataInput().readInt();
          byte a4 = packet.getDataInput().readByte();

          result += "[A3:" + a3 + "]";
          result += "[A4:" + a4 + "]";
          byte a5 = packet.getDataInput().readByte();
          for (int i = 0; i < a5; ++i) {
            short sessionId = packet.getDataInput().readShort();
            int a7 = packet.getDataInput().readInt();
            int hpNow = packet.getDataInput().readInt();
            byte a9 = packet.getDataInput().readByte();
            result += "[A6:" + sessionId + "]";
            result += "[A7:" + a7 + "]";
            result += "[hpNow:" + hpNow + "]";
            result += "[A9:" + a9 + "]";

            byte a10 = packet.getDataInput().readByte();
            for (int j = 0; j < a10; ++j) {
              byte a11 = packet.getDataInput().readByte();
              int a12 = packet.getDataInput().readInt();
              result += "[A11:" + a11 + "]";
              result += "[A12:" + a12 + "]";
            }

            if(sessionId == session.idSessionInGame){
              session.hpNow = hpNow;
            }
          }
        }
      } catch (java.io.IOException e) {
        e.printStackTrace();
      }
    }

    return result;
  }
}
