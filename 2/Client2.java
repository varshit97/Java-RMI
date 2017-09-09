
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.PublicKey;

public class Client2 {

    private static BufferedReader inputLine = null;

    public Client2() {
    }

    public static void main(String[] args) throws IOException {
        inputLine = new BufferedReader(new InputStreamReader(System.in));

        Person bob = new Person();
        System.out.println("Generating Keys....");
        bob.generateKeys();
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Interface2 stub = (Interface2) registry.lookup("Server");
            System.out.println("Sent Client Public Key to Server");
            PublicKey AliceKey = stub.exchangePublicKey(bob.getPublicKey());
            System.out.println("Received Public Key from Server");
            byte[] secretKey = bob.generateCommonSecretKey(AliceKey);
            System.out.println("Generated Secret Key");
            while (true) {
                String input = inputLine.readLine().trim();
                if (input.equals("quit")) {
                    break;
                }
                String number = input.split(" ")[1];
                byte[] encryptedNumber = bob.encrypt(number, secretKey);
                if (input.startsWith("miller")) {
                    byte[] response = stub.MillerRabin(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response, secretKey);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("palindrome")) {
                    byte[] response = stub.palindrome(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response, secretKey);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("fibonacci")) {
                    byte[] response = stub.fibonacci(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response, secretKey);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("utol")) {
                    byte[] response = stub.utol(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response, secretKey);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("ltou")) {
                    byte[] response = stub.ltou(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response, secretKey);
                    System.out.println(decryptedMessage);
                }
            }
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
