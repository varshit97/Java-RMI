
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Client_201402029 {

    private static BufferedReader inputLine = null;

    private Client_201402029() {
    }

    public static void main(String[] args) {
        inputLine = new BufferedReader(new InputStreamReader(System.in));
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Interface1_201402029 stub = (Interface1_201402029) registry.lookup("Server");
            while (true) {
                String input = inputLine.readLine().trim();
                if (input.equals("quit")) {
                    break;
                }
                if (input.startsWith("deposit")) {
                    String ac_no = input.split(" ")[1];
                    double amount = Double.parseDouble(input.split(" ")[2]);
                    String response = stub.deposit(ac_no, amount);
                    System.out.println(response);
                }
                if (input.startsWith("balance")) {
                    String ac_no = input.split(" ")[1];
                    String response = stub.balance(ac_no);
                    System.out.println(response);
                }
                if (input.startsWith("withdraw")) {
                    String ac_no = input.split(" ")[1];
                    double amount = Double.parseDouble(input.split(" ")[2]);
                    String response = stub.withdraw(ac_no, amount);
                    System.out.println(response);
                }
                if (input.startsWith("transactionD")) {
                    String ac_no = input.split(" ")[1];
                    String start_date = input.split(" ")[2];
                    String end_date = input.split(" ")[3];
                    List<List<Object>> response = stub.transaction_details(ac_no, start_date, end_date);
                    System.out.printf("%-22s%-22s%-22s%-22s\n", "T_ID", "T_Date", "T_Time", "Type");
                    for (final List<Object> row : response) {
                        System.out.printf("%-22s%-22s%-22s%-22s\n", row.get(0), row.get(1), row.get(2), row.get(3));
                    }
                }
                if (input.startsWith("transactionA")) {
                    String ac_no = input.split(" ")[1];
                    List<List<Object>> response = stub.all_transactions(ac_no);
                    System.out.printf("%-22s%-22s%-22s%-22s\n", "T_ID", "T_Date", "T_Time", "Type");
                    for (final List<Object> row : response) {
                        System.out.printf("%-22s%-22s%-22s%-22s\n", row.get(0), row.get(1), row.get(2), row.get(3));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
