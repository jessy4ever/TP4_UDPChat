package udpchat;

import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nom d'utilisateur : ");
            String username = sc.nextLine();

            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddr = InetAddress.getByName("localhost");
            int serverPort = 1234;

            Thread receiveThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                while (true) {
                    try {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String msg = new String(packet.getData(), 0, packet.getLength());
                        System.out.println(msg);
                    } catch (Exception e) {
                        break;
                    }
                }
            });
            receiveThread.start();

            System.out.println("Tapez vos messages ('quit' pour quitter)");
            while (true) {
                String msg = sc.nextLine();
                if (msg.equalsIgnoreCase("quit")) break;

                String fullMsg = "[" + username + "] : " + msg;
                byte[] data = fullMsg.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddr, serverPort);
                socket.send(packet);
            }

            socket.close();
            sc.close();
            System.out.println("Déconnexion...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
