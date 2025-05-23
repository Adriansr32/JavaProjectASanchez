package model;

public class BookLoan {
	private int loanId;
	private int bookId;

	public BookLoan(int loanId, int bookId) {
		this.loanId = loanId;
		this.bookId = bookId;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
}
