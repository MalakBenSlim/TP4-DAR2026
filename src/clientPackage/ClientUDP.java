package clientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static java.net.InetAddress.getByName;

/** Etape 2 - Client UDP : envoie des messages prefixes par le nom d'utilisateur. */
public class ClientUDP {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.print("Nom d'utilisateur : ");
        String nom = sc.nextLine();

        DatagramSocket socket = new DatagramSocket(); //client cree une socket UDP

        System.out.println("Tapez vos messages (\"quit\" pour sortir) :");
        while (sc.hasNextLine()) {
            String texte = sc.nextLine();
            if (texte.equalsIgnoreCase("quit"))
                break;
            // on convertit le message (nom+texte) en tableau d'octets pour envoyer au serveur
            byte[] buffer = ("[" + nom + "] : " + texte).getBytes();

            // client prepare le paquet
            DatagramPacket donneesEmises= new DatagramPacket(buffer, buffer.length,getByName("localhost"),1234);
            socket.send(donneesEmises); // client envoie le paquet au serveur
        }
        socket.close();
        sc.close();
    }
}
