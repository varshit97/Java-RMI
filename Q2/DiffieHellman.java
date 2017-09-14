
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

public class DiffieHellman {
    BigInteger x = null, key = null, g = null, p = null;

    private static boolean bitOf(char in) {
        return (in == '1');
    }

    private static char charOf(boolean in) {
        return (in) ? '1' : '0';
    }

    public String stob(String message) {
        String s = message;
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    public String btos(String message) {
        BigInteger val = new BigInteger(message, 2);
        String finalMessage = new String(val.toByteArray());
        return finalMessage;
    }

    public String encrypt(String message) {
        String s_key = key.toString(2);
        String new_message = stob(message);
        int diff;
        String zeros = "";
        if (new_message.length() < s_key.length()) {
            diff = s_key.length() - new_message.length();
            for (int i = 0; i < diff; i++) {
                zeros += "0";
            }
        }
        new_message = zeros + new_message;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < new_message.length(); i++) {
            sb.append(charOf(bitOf(new_message.charAt(i)) ^ bitOf(s_key.charAt(i))));
        }
        return sb.toString();
    }

    public BigInteger generateCommonSecretKey(BigInteger mod) {
        key = mod.modPow(x, p);
        return key;
    }

    public BigInteger generateKeys(BigInteger gi, BigInteger pi) {
        g = gi;
        p = pi;
        x = new BigInteger(256, new Random());
        BigInteger mod = g.modPow(x, p);
        return mod;

    }

    public String getPublicKey() {
        return null;
    }

    public String decrypt(String cipher) {
        String s_key = key.toString(2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cipher.length(); i++) {
            sb.append(charOf(bitOf(cipher.charAt(i)) ^ bitOf(s_key.charAt(i))));
        }
        return btos(sb.toString());
    }
}
