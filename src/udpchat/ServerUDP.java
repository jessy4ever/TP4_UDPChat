package udpchat;

import java.net.*;
import java.util.*;

public class ServerUDP {
    private static final int PORT = 1234;
    private static Set<SocketAddress> clients = new HashSet<>();

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            System.out.println("Serveur UDP lancé sur le port " + PORT);

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength());
                SocketAddress senderAddr = packet.getSocketAddress();

                if (!clients.contains(senderAddr)) {
                    clients.add(senderAddr);
                    System.out.println("Nouveau client : " + senderAddr);
                }

                System.out.println("Message reçu de " + senderAddr + " : " + msg);

                for (SocketAddress addr : clients) {
                    if (!addr.equals(senderAddr)) {
                        DatagramPacket sendPacket = new DatagramPacket(
                                msg.getBytes(), msg.length(), addr);
                        socket.send(sendPacket);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
