package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/** Etape 2 - Serveur UDP unidirectionnel : recoit et affiche. */
public class ServeurUDP {

    public static final int PORT = 1234;
    public static void main(String[] args) throws Exception {

        // Liaison du socket a l'adresse locale, port 1234
        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(PORT));
        System.out.println("Serveur UDP en ecoute sur le port " + PORT + "...");

        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket paquet = new DatagramPacket(buffer, buffer.length); //serveur attend un paquet
            socket.receive(paquet); // bloquant jusqu'a il recoit le paquet

            //serveur lit les donnees recues
            String donnees = new String(paquet.getData(), 0, paquet.getLength());

            System.out.println("De " + paquet.getAddress().getHostAddress() + ":" + paquet.getPort() + " -> " + donnees);
        }
    }
}
