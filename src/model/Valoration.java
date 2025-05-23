package model;

public class Valoration {
    private int valorationId;
    private int userId;
    private int bookId;
    private int score;
    private String comments;

    public Valoration(int valorationId, int userId, int bookId, int score, String comments) {
        this.valorationId = valorationId;
        this.userId = userId;
        this.bookId = bookId;
        this.score = score;
        this.comments = comments;
    }

    public int getValorationId() { return valorationId; }
    public void setValorationId(int valorationId) { this.valorationId = valorationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
