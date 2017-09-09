
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

public interface Interface2 extends Remote {

    String sayHello() throws RemoteException;

    byte[] MillerRabin(byte[] number) throws RemoteException;

    byte[] palindrome(byte[] number) throws RemoteException;

    byte[] fibonacci(byte[] number) throws RemoteException;

    byte[] utol(byte[] number) throws RemoteException;

    byte[] ltou(byte[] number) throws RemoteException;

    PublicKey exchangePublicKey(PublicKey publicKey) throws RemoteException;
}
