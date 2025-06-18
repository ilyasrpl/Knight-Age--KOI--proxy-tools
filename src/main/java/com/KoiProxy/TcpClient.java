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
  public PlayerSession session; // New field

  public TcpClient(DataInputStream disClient, DataOutputStream dosClient, Encryption enc, PlayerSession session) {
    try {
      socket = new Socket(AppEndpoint.host, AppEndpoint.port);
      this.dos = new DataOutputStream(socket.getOutputStream());
      this.dis = new DataInputStream(socket.getInputStream());
      this.disClient = disClient;
      this.dosClient = dosClient;
      this.enc = enc;
      this.session = session; // Initialize new field
      this.running = true;
      startReceiveThread();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void startReceiveThread() {
    Thread receiveThread = new Thread(() -> {
      while (running) {
        try {
          byte packetId = dis.readByte();
          dosClient.writeByte(packetId);
          dosClient.flush();
          packetId = enc.swap(packetId);

          int messageLength = 0;
          if (packetId == -51 || packetId == -52 || packetId == -54 || packetId == 126) {
            if (packetId == 126) {
              packetId = dis.readByte();
              dosClient.writeByte(packetId);
              dosClient.flush();
              packetId = enc.swap(packetId);
            }
            byte length1 = dis.readByte();
            byte length2 = dis.readByte();
            byte length3 = dis.readByte();
            byte length4 = dis.readByte();
            dosClient.writeByte(length1);
            dosClient.writeByte(length2);
            dosClient.writeByte(length3);
            dosClient.writeByte(length4);
            dosClient.flush();
            messageLength = ((enc.swap(length4) & 0xFF)) | ((enc.swap(length3) & 0xFF) << 8)
                | ((enc.swap(length2) & 0xFF) << 16) | ((enc.swap(length1) & 0xFF) << 24);

          } else {

            byte length1 = dis.readByte();
            byte length2 = dis.readByte();
            dosClient.writeByte(length1);
            dosClient.writeByte(length2);
            dosClient.flush();
            messageLength = ((enc.swap(length1) & 0xFF) << 8) | (enc.swap(length2) & 0xFF);
          }

          byte[] messageBytes = new byte[messageLength];
          int bytesRead = 0;
          int totalBytesRead = 0;

          while (bytesRead != -1 && totalBytesRead < messageLength) {
            bytesRead = dis.read(messageBytes, totalBytesRead, messageLength - totalBytesRead);

            if (bytesRead > 0) {
              totalBytesRead += bytesRead;
            }
          }
          dosClient.write(messageBytes);
          dosClient.flush();

          Packet packet = new Packet(packetId, messageBytes);

          // XOR messageBytes
          for (int i = 0; i < messageBytes.length; i++) {
            messageBytes[i] = enc.swap(messageBytes[i]);
          }
          if (packet.type == -40) {
            enc.setKey(new byte[] { messageBytes[1] });
          }

          final byte finalMessageId = packetId;
          final byte[] finalMessageBytes = messageBytes;
          new Thread(() -> {

            Packet packet2 = new Packet(finalMessageId, finalMessageBytes);
            String logStr = new SMsgparser(packet, session).toString();
            if (App.rawModeCheckBox.isSelected())
              App.addRightLog(packet2.toString());
            if (App.blockModeCheckBox.isSelected())
              App.addRightLog(logStr);
            if (App.rawModeCheckBox.isSelected() || App.blockModeCheckBox.isSelected())
              App.refreshTAreaRight();
          }).start();
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
