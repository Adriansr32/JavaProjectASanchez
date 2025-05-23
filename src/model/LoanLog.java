package model;

import java.time.LocalDateTime;

public class LoanLog {
    private int logId;
    private int loanId;
    private int userId;
    private String action;
    private LocalDateTime logTime;

    public LoanLog(int logId, int loanId, int userId, String action, LocalDateTime logTime) {
        this.logId = logId;
        this.loanId = loanId;
        this.userId = userId;
        this.action = action;
        this.logTime = logTime;
    }

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public int getLoanId() { return loanId; }
    public void setLoanId(int loanId) { this.loanId = loanId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public LocalDateTime getLogTime() { return logTime; }
    public void setLogTime(LocalDateTime logTime) { this.logTime = logTime; }
}
