package udpchat;

import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = sc.nextLine();

            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddr = InetAddress.getByName("localhost");
            int serverPort = 1234;

            System.out.println("Tapez vos messages (ou 'quit' pour quitter):");

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
            System.out.println("Client terminé.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
