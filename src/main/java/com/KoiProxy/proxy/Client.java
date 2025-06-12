package com.KoiProxy.proxy;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 1234;

        try (
            Socket socket = new Socket(serverAddress, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Terhubung ke server.");
            byte messageId = 1;

            while (true) {
                System.out.print("Ketik pesan ('exit' untuk keluar): ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) break;

                byte[] messageBytes = message.getBytes("UTF-8");

                // Kirim: [1 byte ID][4 byte panjang][pesan]
                dos.writeByte(messageId);
                dos.writeInt(messageBytes.length);
                dos.write(messageBytes);

                // Baca balasan dari server (optional)
                byte responseId = dis.readByte();
                int responseLength = dis.readInt();
                byte[] responseBytes = new byte[responseLength];
                dis.readFully(responseBytes);
                String response = new String(responseBytes, "UTF-8");
                System.out.println("Dari server (ID " + responseId + "): " + response);

                messageId++; // Increment ID jika mau berbeda-beda
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

