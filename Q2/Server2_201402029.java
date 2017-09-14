
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.SecureRandom;

public class Server2_201402029 implements Interface2_201402029 {

    public PublicKey BobKey = null;
    public DiffieHellman alice = new DiffieHellman();
    BigInteger secretKey = null, p = null, g = null;
    BigInteger Amodulus = null;

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {
        try {
            Server2_201402029 obj = new Server2_201402029();
            Interface2_201402029 stub = (Interface2_201402029) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public Server2_201402029() {
        System.out.println("Generating Keys....");
        int bitLength = 512;
        SecureRandom rnd = new SecureRandom();
        p = BigInteger.probablePrime(bitLength, rnd);
        g = BigInteger.probablePrime(bitLength, rnd);
        Amodulus = alice.generateKeys(g, p);
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger exchangePublicKey(BigInteger Bmodulus) {
        System.out.println("Received Public Key from Client");
        secretKey = alice.generateCommonSecretKey(Bmodulus);
        System.out.println("Generated Secret Key");
        System.out.println("Sent Server Public Key to Client");
        return Amodulus;
    }

    public String MillerRabin(String number) {
        BigInteger n = new BigInteger(alice.decrypt(number));
        System.out.println(alice.decrypt(number));
        int certainty = Integer.parseInt("1000000");
        String normalText = n.isProbablePrime(certainty) ? "1" : "0";
        String encryptedText = alice.encrypt(normalText);
        return encryptedText;
    }

    public String palindrome(String number) {
        String normalText;
        if (alice.decrypt(number).equals(new StringBuilder(alice.decrypt(number)).reverse().toString())) {
            normalText = "Palindrome";
        } else {
            normalText = "Not a Palindrome";
        }
        String encryptedText = alice.encrypt(normalText);
        return encryptedText;
    }

    public String fibonacci(String num) {
        long n = Long.parseLong(alice.decrypt(num));
        if (n <= 0) {
            String normalText = Integer.toString(0);
            String encryptedText = alice.encrypt(normalText);
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
        String encryptedText = alice.encrypt(normalText);
        return encryptedText;
    }

    public String utol(String s) {
        String normalText = alice.decrypt(s).toLowerCase();
        String encryptedText = alice.encrypt(normalText);
        return encryptedText;
    }

    public String ltou(String s) {
        String normalText = alice.decrypt(s).toUpperCase();
        String encryptedText = alice.encrypt(normalText);
        return encryptedText;
    }
}
