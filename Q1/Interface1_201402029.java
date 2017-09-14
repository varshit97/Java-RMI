
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Interface1_201402029 extends Remote {

    String sayHello() throws RemoteException;

    String deposit(String ID, double amount) throws RemoteException;

    String withdraw(String ID, double amount) throws RemoteException;

    String balance(String ID) throws RemoteException;

    List<List<Object>> transaction_details(String ID, String start_date, String end_date) throws RemoteException;

    List<List<Object>> all_transactions(String ac_no) throws RemoteException;
}
