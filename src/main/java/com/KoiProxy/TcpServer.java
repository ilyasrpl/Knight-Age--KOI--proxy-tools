package com.KoiProxy;

import java.io.*;
import java.net.*;

public class TcpServer {
    public static void start() {
        int port = 19130;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();

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
                    client.dos.writeByte(messageId);
                    client.dos.flush();
                    messageId = enc.swap(messageId);
                } catch (EOFException e) {
                    break; // Client disconnect
                }

                // 2. Baca 4 byte untuk panjang pesan
                messageLength = 0;
                byte length1 = dis.readByte();
                byte length2 = dis.readByte();
                client.dos.writeByte(length1);
                client.dos.writeByte(length2);
                client.dos.flush();
                messageLength |= (enc.swap(length1) & 0xFF) << 8;
                messageLength |= (enc.swap(length2) & 0xFF);

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

                client.dos.write(messageBytes);
                client.dos.flush();

                // XOR messageBytes
                for (int i = 0; i < messageBytes.length; i++) {
                    messageBytes[i] = enc.swap(messageBytes[i]);
                }

                final byte finalMessageId = messageId;
                final byte[] finalMessageBytes = messageBytes;
                new Thread(() -> {
                    Packet packet = new Packet(finalMessageId, finalMessageBytes);
                    if (App.rawModeCheckBox.isSelected())
                        App.addLeftLog(packet.toString());
                    if (App.blockModeCheckBox.isSelected())
                        App.addLeftLog(new CMsgparser(packet).toString());
                    App.refreshTAreaLeft();
                }).start();
            }

        } catch (IOException e) {
            System.out.println("Koneksi dengan client terputus.");
            System.out.println(e.getMessage());
        }
    }

}
