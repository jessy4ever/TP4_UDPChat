package udpchat;

import java.net.*;

public class ServerUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(1234);
            socket.bind(address);
            System.out.println("Serveur UDP en écoute sur le port 1234...");

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Reçu de " + packet.getAddress() + ":" + packet.getPort() + " → " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
