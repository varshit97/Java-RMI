
import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface2_201402029 extends Remote {

    String sayHello() throws RemoteException;

    BigInteger getG() throws RemoteException;
    
    BigInteger getP() throws RemoteException;
    
    String MillerRabin(String number) throws RemoteException;

    String palindrome(String number) throws RemoteException;

    String fibonacci(String number) throws RemoteException;

    String utol(String number) throws RemoteException;

    String ltou(String number) throws RemoteException;

    BigInteger exchangePublicKey(BigInteger modulus) throws RemoteException;
}
