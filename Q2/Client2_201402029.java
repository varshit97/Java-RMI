
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client2_201402029 {

    private static BufferedReader inputLine = null;

    public Client2_201402029() {
    }

    public static void main(String[] args) throws IOException {
        inputLine = new BufferedReader(new InputStreamReader(System.in));

        DiffieHellman bob = new DiffieHellman();
        System.out.println("Generating Keys....");
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Interface2_201402029 stub = (Interface2_201402029) registry.lookup("Server");
            System.out.println("Sent Client Public Key to Server");
            BigInteger g = stub.getG();
            BigInteger p = stub.getP();
            BigInteger Bmodulus = bob.generateKeys(g, p);
            BigInteger Amodulus = stub.exchangePublicKey(Bmodulus);
            System.out.println("Received Public Key from Server");
            BigInteger secretKey = bob.generateCommonSecretKey(Amodulus);
            System.out.println("Generated Secret Key");
            while (true) {
                String input = inputLine.readLine().trim();
                if (input.equals("quit")) {
                    break;
                }
                String number = input.split(" ")[1];
                String encryptedNumber = bob.encrypt(number);
                if (input.startsWith("miller")) {
                    String response = stub.MillerRabin(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response);
                    if(decryptedMessage.equals("1"))
                    {
                        System.out.println("Prime");
                    }
                    else
                    {
                        System.out.println("Not Prime");
                    }
                }
                if (input.startsWith("palindrome")) {
                    String response = stub.palindrome(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("fibonacci")) {
                    String response = stub.fibonacci(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("utol")) {
                    String response = stub.utol(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response);
                    System.out.println(decryptedMessage);
                }
                if (input.startsWith("ltou")) {
                    String response = stub.ltou(encryptedNumber);
                    String decryptedMessage = bob.decrypt(response);
                    System.out.println(decryptedMessage);
                }
            }
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}
