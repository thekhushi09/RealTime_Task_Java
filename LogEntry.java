import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class LogEntry {

    private static AtomicInteger counter = new AtomicInteger(1);

    private int logId;
    private String accountNumber;
    private ActionType actionType;
    private double amount;
    private LocalDateTime timestamp;
    private Status status;

    public LogEntry(String accountNumber, ActionType actionType, double amount, Status status) {
        this.logId = counter.getAndIncrement();
        this.accountNumber = accountNumber;
        this.actionType = actionType;
        this.amount = amount;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public int getLogId() { return logId; }
    public String getAccountNumber() { return accountNumber; }
    public ActionType getActionType() { return actionType; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Status getStatus() { return status; }

    @Override
    public String toString() {
        return logId + " | " + accountNumber + " | " + actionType + " | " + amount +
               " | " + status + " | " + timestamp;
    }
}