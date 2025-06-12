package com.KoiProxy;

import java.io.*;
import java.net.*;

public class TcpClient implements AutoCloseable {
  public Socket socket;
  public DataOutputStream dos;
  public DataInputStream dis;
  public DataInputStream disClient;
  public DataOutputStream dosClient;
  public Encryption enc;
  public boolean running;

  public TcpClient(DataInputStream disClient, DataOutputStream dosClient, Encryption enc) {
    try {
      socket = new Socket(AppEndpoint.host, AppEndpoint.port);
      this.dos = new DataOutputStream(socket.getOutputStream());
      this.dis = new DataInputStream(socket.getInputStream());
      this.disClient = disClient;
      this.dosClient = dosClient;
      this.enc = enc;
      this.running = true;
      App.addText2("TcpClient :");
      startReceiveThread();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendPacket(Packet packet) {
    try {
      // Enkripsi type dengan XOR
      dos.write(enc.swap(packet.type));
      
      // Enkripsi length dengan XOR
      byte[] lengthBytes = new byte[2];
      lengthBytes[0] = (byte)((packet.getBytes().length >> 8) & 0xFF);
      lengthBytes[1] = (byte)(packet.getBytes().length & 0xFF);
      dos.write(enc.swap(lengthBytes[0]));
      dos.write(enc.swap(lengthBytes[1]));
      
      // Enkripsi message bytes dengan XOR
      byte[] messageBytes = packet.getBytes();
      for(int i = 0; i < messageBytes.length; i++) {
        messageBytes[i] = enc.swap(messageBytes[i]);
      }
      dos.write(messageBytes);
      
      dos.flush();

      App.addText1(packet.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void sendPacketToClient(Packet packet) {
    try {
      dosClient.write(packet.type);
      if (packet.type == -40) {
        App.addText2("setKey");
        byte[] key = new byte[1];
        key[0] = packet.getBytes()[1];
        enc.setKey(key);
      }
      dosClient.writeShort(packet.getBytes().length);
      dosClient.write(packet.getBytes());
      App.addText2(packet.toString());
      dosClient.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void startReceiveThread() {
    Thread receiveThread = new Thread(() -> {
      while (running) {
        try {
          // Contoh: baca panjang data dulu (misal 4 byte int), lalu baca data
          // byte msg = dis.readByte();
          // App.addText2(String.valueOf(msg) + " ");
          // dosClient.writeByte(msg);
          byte packetId = dis.readByte();
          packetId = enc.swap(packetId);

          byte length1 = dis.readByte();
          byte length2 = dis.readByte();
          int messageLength = ((enc.swap(length1) & 0xFF) << 8) | (enc.swap(length2) & 0xFF);

          byte[] messageBytes = new byte[messageLength];
          int bytesRead = 0;
          int totalBytesRead = 0;

          while (bytesRead != -1 && totalBytesRead < messageLength) {
            bytesRead = dis.read(messageBytes, totalBytesRead, messageLength - totalBytesRead);

            if (bytesRead > 0) {
              totalBytesRead += bytesRead;
            }
          }

          Packet packet = new Packet(packetId, messageBytes);
          sendPacketToClient(packet);

          // Lakukan sesuatu dengan data yang diterima
          // onPacketReceived(buffer);
        } catch (IOException e) {
          System.out.println("Terputus dari server atau error saat membaca data.");
          running = false;
          close(); // pastikan koneksi ditutup saat error
        }
      }
    });
    receiveThread.setDaemon(true); // thread akan otomatis mati saat program utama selesai
    receiveThread.start();
  }

  @Override
  public void close() {
    running = false;
    try {
      if (this.dis != null)
        this.dis.close();
      if (this.dos != null)
        this.dos.close();
      if (this.socket != null)
        this.socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
