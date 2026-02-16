import java.util.*;
import java.util.concurrent.*;

public class LogManager implements LogService {

    // Thread-safe data structures
    private List<LogEntry> logs = Collections.synchronizedList(new ArrayList<>());
    private Map<String, List<LogEntry>> accountMap = new ConcurrentHashMap<>();
    private Deque<LogEntry> recentLogs = new ConcurrentLinkedDeque<>();

    // Add Log
    @Override
    public synchronized void addLog(LogEntry log) {
        logs.add(log);

        accountMap.putIfAbsent(log.getAccountNumber(),
                Collections.synchronizedList(new ArrayList<>()));

        accountMap.get(log.getAccountNumber()).add(log);

        recentLogs.addFirst(log);
    }

    // Get Logs by Account
    public List<LogEntry> getLogsByAccount(String acc) {
        return accountMap.getOrDefault(acc, new ArrayList<>());
    }

    // Recent N Logs
    public List<LogEntry> getRecentLogs(int n) {
        List<LogEntry> res = new ArrayList<>();
        int count = 0;
        for (LogEntry l : recentLogs) {
            if (count++ == n) break;
            res.add(l);
        }
        return res;
    }

    // Search by Action Type
    public List<LogEntry> searchByAction(ActionType type) {
        List<LogEntry> res = new ArrayList<>();
        synchronized (logs) {
            for (LogEntry l : logs) {
                if (l.getActionType() == type)
                    res.add(l);
            }
        }
        return res;
    }

    // Suspicious Detection
    public List<LogEntry> detectSuspicious() {
        List<LogEntry> suspicious = new ArrayList<>();

        for (String acc : accountMap.keySet()) {
            List<LogEntry> list = accountMap.get(acc);
            if (list == null) continue;

            synchronized (list) {
                int start = Math.max(0, list.size() - 5);
                int failedCount = 0;

                for (int i = start; i < list.size(); i++) {
                    LogEntry l = list.get(i);

                    if (l.getActionType() == ActionType.FAILED_LOGIN)
                        failedCount++;

                    if (l.getActionType() == ActionType.WITHDRAW && l.getAmount() > 50000)
                        suspicious.add(l);
                }

                if (failedCount > 3) {
                    for (int i = start; i < list.size(); i++) {
                        if (list.get(i).getActionType() == ActionType.FAILED_LOGIN)
                            suspicious.add(list.get(i));
                    }
                }
            }
        }
        return suspicious;
    }
}