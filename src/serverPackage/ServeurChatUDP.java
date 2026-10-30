package serverPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;

/** Etape 3 - Serveur de chat UDP : enregistre les clients et diffuse les messages. */
public class ServeurChatUDP {
    public static final int PORT = 1234;
    public static void main(String[] args) throws Exception {

        // Liaison du socket a l'adresse locale, port 1234
        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(PORT));
        System.out.println("Serveur UDP en ecoute sur le port " + PORT + "...");

        // liste des adresses des clients qui ont envoye un message ("connexion" simulee)
        ArrayList<SocketAddress> clients = new ArrayList<SocketAddress>();

        byte[] buffer = new byte[1024];

        while (true) {
            DatagramPacket paquet = new DatagramPacket(buffer, buffer.length); //serveur attend un paquet
            socket.receive(paquet); // bloquant jusqu'a il recoit le paquet

            //serveur lit le donnees recues
            String donnees = new String(paquet.getData(), 0, paquet.getLength());
            SocketAddress expediteur = paquet.getSocketAddress(); // @IP + port de l'expediteur (celui qui envoit)
            System.out.println("De " + expediteur + " -> " + donnees);

            // enregistrer l'expediteur s'il n'est pas deja dans la liste
            if (!clients.contains(expediteur)) {
                clients.add(expediteur);
            }

            // envoyer le message a tous les autres clients
            byte[] msg = donnees.getBytes();  //convertir le msg en octets pour envoyer aux clients
            for (SocketAddress client : clients) {
                if (!client.equals(expediteur)) {
                    DatagramPacket donneesEnvoyees=new DatagramPacket(msg, msg.length, client);
                    socket.send(donneesEnvoyees);
                }
            }


        }
    }
}
