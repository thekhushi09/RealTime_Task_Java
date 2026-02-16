import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LogManager manager = new LogManager();

        while (true) {
            System.out.println("\n--- Banking Log System ---");
            System.out.println("1. Add Log");
            System.out.println("2. Get Logs by Account");
            System.out.println("3. Get Recent Logs");
            System.out.println("4. Detect Suspicious Activity");
            System.out.println("5. Search by Action Type");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Account Number: ");
                    String acc = sc.next();

                    System.out.print("Action (DEPOSIT/WITHDRAW/TRANSFER/LOGIN/FAILED_LOGIN): ");
                    ActionType type = ActionType.valueOf(sc.next());

                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();

                    System.out.print("Status (SUCCESS/FAILED): ");
                    Status status = Status.valueOf(sc.next());

                    manager.addLog(new LogEntry(acc, type, amt, status));
                    System.out.println("Log Added Successfully!");
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    acc = sc.next();
                    for (LogEntry l : manager.getLogsByAccount(acc))
                        System.out.println(l);
                    break;

                case 3:
                    System.out.print("Enter N: ");
                    int n = sc.nextInt();
                    for (LogEntry l : manager.getRecentLogs(n))
                        System.out.println(l);
                    break;

                case 4:
                    System.out.println("Suspicious Logs:");
                    for (LogEntry l : manager.detectSuspicious())
                        System.out.println(l);
                    break;

                case 5:
                    System.out.print("Action Type: ");
                    type = ActionType.valueOf(sc.next());
                    for (LogEntry l : manager.searchByAction(type))
                        System.out.println(l);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
            }
        }
    }
}