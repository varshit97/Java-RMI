
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Person {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String secretMessage;

    public byte[] encrypt(final String message, byte[] secretKey) {

        byte[] encryptedMessage = null;
        try {
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey, "DES");
            final Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            encryptedMessage = cipher.doFinal(message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedMessage;
    }

    public byte[] generateCommonSecretKey(PublicKey receivedPublicKey) {

        byte[] secretKey = null;
        try {
            final KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(receivedPublicKey, true);

            secretKey = shortenSecretKey(keyAgreement.generateSecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public void generateKeys() {

        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(1024);

            final KeyPair keyPair = keyPairGenerator.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {

        return publicKey;
    }

    public String decrypt(final byte[] message, byte[] secretKey) {

        try {
            final SecretKeySpec keySpec = new SecretKeySpec(secretKey, "DES");
            final Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            secretMessage = new String(cipher.doFinal(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretMessage;
    }

    public void whisperTheSecretMessage() {

        System.out.println(secretMessage);
    }

    private byte[] shortenSecretKey(final byte[] longKey) {

        try {
            final byte[] shortenedKey = new byte[8];

            System.arraycopy(longKey, 0, shortenedKey, 0, shortenedKey.length);

            return shortenedKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
