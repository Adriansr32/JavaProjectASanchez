package model;

import java.time.LocalDate;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private byte[] cover;
    private String editorial;
    private LocalDate yearPublication;

    public Book(int bookId, String title, String author, String isbn, byte[] cover, String editorial, LocalDate yearPublication) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.cover = cover;
        this.editorial = editorial;
        this.yearPublication = yearPublication;
    }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public byte[] getCover() { return cover; }
    public void setCover(byte[] cover) { this.cover = cover; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public LocalDate getYearPublication() { return yearPublication; }
    public void setYearPublication(LocalDate yearPublication) { this.yearPublication = yearPublication; }
}
