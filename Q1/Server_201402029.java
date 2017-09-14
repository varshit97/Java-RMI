
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class Transaction {

    String date, time;
    String type, ac_no;
    int t_id;
    
    public Transaction(int t_id, String ac_no, String date, String time, String type) {
        this.t_id = t_id;
        this.ac_no = ac_no;
        this.date = date;
        this.time = time;
        this.type = type;
    }
}

class Bank {

    String name, ac_type, contact_info;
    double balance;
    int t_count;

    public Bank(String name, String ac_type, String contact_info, double balance, int t_count) {
        this.name = name;
        this.ac_type = ac_type;
        this.contact_info = contact_info;
        this.balance = balance;
        this.t_count = t_count;
    }
}

public class Server_201402029 implements Interface1_201402029 {

    public HashMap<String, Bank> accounts = new HashMap<String, Bank>();
    public HashMap<Integer, Transaction> allTransactions = new HashMap<Integer, Transaction>();
    public int t_id = 1;

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {
        try {
            Server_201402029 obj = new Server_201402029();
            Interface1_201402029 stub = (Interface1_201402029) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public Server_201402029() {
        Bank p1 = new Bank("varshit", "premium", "12345", 10000.0, 0);
        Bank p2 = new Bank("vishal", "premium", "12346", 42865.43, 0);
        accounts.put("111111", p1);
        accounts.put("222222", p2);
    }

    public String deposit(String ac_no, double amount) {
        if (accounts.get(ac_no) != null) {
            Bank details = accounts.get(ac_no);
            details.balance += amount;
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd/HH.mm.ss").format(new Date());
            String date = timeStamp.split("/")[0];
            String time = timeStamp.split("/")[1];
            details.t_count += 1;
            Transaction t = new Transaction(details.t_count, ac_no, date, time, "Credit");
            allTransactions.put(t_id, t);
            t_id += 1;
            return "Balance Incremented " + details.balance;
        }
        return "This user has no account in this bank";
    }

    public String withdraw(String ac_no, double amount) {
        if (accounts.get(ac_no) != null && accounts.get(ac_no).balance - amount >= 0) {
            Bank details = accounts.get(ac_no);
            details.balance -= amount;
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd/HH.mm.ss").format(new Date());
            String date = timeStamp.split("/")[0];
            String time = timeStamp.split("/")[1];
            details.t_count += 1;
            Transaction t = new Transaction(details.t_count, ac_no, date, time, "Debit");
            allTransactions.put(t_id, t);
            t_id += 1;
            return "New balance is " + details.balance;
        }
        return "No sufficient funds";
    }

    public String balance(String ac_no) {
        if (accounts.get(ac_no) != null) {
            Bank details = accounts.get(ac_no);
            return "Balance is " + details.balance;
        }
        return "This user has no account in this bank";
    }

    public List<List<Object>> transaction_details(String ac_no, String start_date, String end_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<List<Object>> TData = new ArrayList<List<Object>>();
        int count = 0;
        for (HashMap.Entry<Integer, Transaction> entry : allTransactions.entrySet()) {
            Transaction value = allTransactions.get(entry.getKey());
            String checkDate = value.date;
            try {
                Date date1 = sdf.parse(start_date);
                Date date2 = sdf.parse(checkDate);
                Date date3 = sdf.parse(end_date);
                if (date2.compareTo(date1) > 0 && date2.compareTo(date3) < 0 && value.ac_no.equals(ac_no)) {
                    TData.add(Arrays.asList(value.t_id, value.date, value.time, value.type));
                }
            } catch (ParseException ex) {
                Logger.getLogger(Server_201402029.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return TData;
    }

    public List<List<Object>> all_transactions(String ac_no) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<List<Object>> TData = new ArrayList<List<Object>>();
        int count = 0;
        for (HashMap.Entry<Integer, Transaction> entry : allTransactions.entrySet()) {
            Transaction value = allTransactions.get(entry.getKey());
            if(value.ac_no.equals(ac_no))
            {
                TData.add(Arrays.asList(value.t_id, value.date, value.time, value.type));
            }
        }
        return TData;
    }
}
