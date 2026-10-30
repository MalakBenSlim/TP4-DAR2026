package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static java.net.InetAddress.getByName;

/** Etape 3 - Client de chat UDP : le thread principal envoie, le thread Reception recoit. */
public class ClientChatUDP {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.print("Nom d'utilisateur : ");
        String nom = sc.nextLine();

        DatagramSocket socket = new DatagramSocket(); //client cree une socket UDP
        InetAddress adresseServeur = InetAddress.getByName("localhost"); // recuperer l'@ du serveur

        // thread qui recoit et affiche les messages des autres clients
        new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(paquet);
                    System.out.println(new String(paquet.getData(), 0, paquet.getLength()));
                }
            } catch (Exception e) {
                // socket fermee : fin du thread
            }
        }).start();

        // le 1er message sert a se faire enregistrer par le serveur
        String texte = "a rejoint le chat";

        while (true) {

            byte[] buffer = ("[" + nom + "] : " + texte).getBytes();
            socket.send(new DatagramPacket(buffer, buffer.length, adresseServeur, 1234));

            if (texte.equals("quit"))
                break;
            texte = sc.nextLine();
        }
        socket.close();  // arrete aussi le thread de reception
        sc.close();
    }
}
