package model;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int userId;
    private LocalDate dateLoan;
    private LocalDate dateReturn;
    private String status;

    public Loan(int loanId, int userId, LocalDate dateLoan, LocalDate dateReturn, String status) {
        this.loanId = loanId;
        this.userId = userId;
        this.dateLoan = dateLoan;
        this.dateReturn = dateReturn;
        this.status = status;
    }

    public int getLoanId() { return loanId; }
    public void setLoanId(int loanId) { this.loanId = loanId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public LocalDate getDateLoan() { return dateLoan; }
    public void setDateLoan(LocalDate dateLoan) { this.dateLoan = dateLoan; }

    public LocalDate getDateReturn() { return dateReturn; }
    public void setDateReturn(LocalDate dateReturn) { this.dateReturn = dateReturn; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
