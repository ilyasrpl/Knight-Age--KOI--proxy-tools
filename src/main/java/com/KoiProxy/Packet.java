package com.KoiProxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class Packet {
   public byte type;
   private ByteArrayOutputStream b = null;
   private DataOutputStream c = null;
   private ByteArrayInputStream d = null;
   private DataInputStream e = null;
   private byte[] f;

   public Packet(byte var1, byte[] var2) {
      this.type = var1;
      this.d = new ByteArrayInputStream(var2);
      this.e = new DataInputStream(this.d);
      this.f = var2;
   }

   public final byte[] getBytes() {
      return this.f;
   }


   public final DataInputStream getDataInput() {
      return this.e;
   }

   public final DataOutputStream getDataOuput() {
      return this.c;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("type : ").append(String.valueOf(type)).append(", msg: ");
      for (byte b : f) {
         sb.append(String.format("%02x ", b));
      }
      return sb.toString().trim();
   }

}
