package com.KoiProxy;

import java.io.*;
import java.net.*;

public class TcpServer {
    public static void start() {
        int port = 19130;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                App.addText1("Client terhubung");

                // Tangani client dalam thread terpisah
                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            
        ) {
            Encryption enc = new Encryption();
            TcpClient client = new TcpClient(dis, dos, enc);
            while (true) {
                byte messageId;
                int messageLength;
                byte[] messageBytes;

                // 1. Baca 1 byte untuk message ID
                try {
                    messageId = dis.readByte();
                    App.addText1("debug " +String.valueOf(messageId) + "\n");
                    messageId = enc.swap(messageId);
                    App.addText1("debug " +String.valueOf(messageId) + "\n");
                } catch (EOFException e) {
                    break; // Client disconnect
                }

                // 2. Baca 4 byte untuk panjang pesan
                messageLength = 0;
                messageLength |= (enc.swap(dis.readByte()) & 0xFF) << 8;
                messageLength |= (enc.swap(dis.readByte()) & 0xFF);
                

                // 3. Baca isi pesan sepanjang messageLength
                messageBytes = new byte[messageLength];
                int bytesRead = 0;
                int totalBytesRead = 0;

                while (bytesRead != -1 && totalBytesRead < messageLength) {
                    bytesRead = dis.read(messageBytes, totalBytesRead, messageLength - totalBytesRead);
                    
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                    }
                }

                Packet packet = new Packet(messageId, messageBytes);
                client.sendPacket(packet);

                String message = bytesToHex(messageBytes);

            }

        } catch (IOException e) {
            System.out.println("Koneksi dengan client terputus.");
        }
    }



    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b));  // lowercase hex, use %02X for uppercase
        }
        return sb.toString().trim();  // remove trailing space
    }
}
