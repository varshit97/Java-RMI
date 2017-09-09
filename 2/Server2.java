
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigInteger;
import java.security.PublicKey;

public class Server2 implements Interface2 {

    public PublicKey BobKey = null;
    public Person alice = new Person();
    byte[] secretKey = null;

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {
        try {
            Server2 obj = new Server2();
            Interface2 stub = (Interface2) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public Server2() {
        System.out.println("Generating Keys....");
        alice.generateKeys();
    }

    public PublicKey exchangePublicKey(PublicKey publicKey) {
        System.out.println("Received Public Key from Client");
        BobKey = publicKey;
        secretKey = alice.generateCommonSecretKey(BobKey);
        System.out.println("Generated Secret Key");
        System.out.println("Sent Server Public Key to Client");
        return alice.getPublicKey();
    }

    public byte[] MillerRabin(byte[] number) {
        BigInteger n = new BigInteger(alice.decrypt(number, secretKey));
        int certainty = Integer.parseInt("1000000");
        String normalText = n.isProbablePrime(certainty) ? "prime" : "composite";
        byte[] encryptedText = alice.encrypt(normalText, secretKey);
        return encryptedText;
    }

    public byte[] palindrome(byte[] number) {
        String normalText;
        if (alice.decrypt(number, secretKey).equals(new StringBuilder(alice.decrypt(number, secretKey)).reverse().toString())) {
            normalText = "Palindrome";
        } else {
            normalText = "Not a Palindrome";
        }
        byte[] encryptedText = alice.encrypt(normalText, secretKey);
        return encryptedText;
    }

    public byte[] fibonacci(byte[] num) {
        long n = Long.parseLong(alice.decrypt(num, secretKey));
        if (n <= 0) {
            String normalText = Integer.toString(0);
            byte[] encryptedText = alice.encrypt(normalText, secretKey);
            return encryptedText;
        }

        long i = (int) (n - 1);
        long a = 1, b = 0, c = 0, d = 1, tmp1, tmp2;

        while (i > 0) {
            if (i % 2 != 0) {
                tmp1 = d * b + c * a;
                tmp2 = d * (b + a) + c * b;
                a = tmp1;
                b = tmp2;
            }

            tmp1 = (long) (Math.pow(c, 2) + Math.pow(d, 2));
            tmp2 = d * (2 * c + d);

            c = tmp1;
            d = tmp2;

            i = i / 2;
        }
        String normalText = Long.toString(a + b);
        byte[] encryptedText = alice.encrypt(normalText, secretKey);
        return encryptedText;
    }

    public byte[] utol(byte[] s) {
        String normalText = alice.decrypt(s, secretKey).toLowerCase();
        byte[] encryptedText = alice.encrypt(normalText, secretKey);
        return encryptedText;
    }

    public byte[] ltou(byte[] s) {
        String normalText = alice.decrypt(s, secretKey).toUpperCase();
        byte[] encryptedText = alice.encrypt(normalText, secretKey);
        return encryptedText;
    }
}
