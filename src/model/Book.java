package model;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa un llibre a la biblioteca digital.
 * Conté informació com el títol, autor, ISBN, portada, editorial i any de publicació.
 */
public class Book {
    /** Identificador únic del llibre. */
    private int bookId;
    /** Títol del llibre. */
    private String title;
    /** Autor del llibre. */
    private String author;
    /** ISBN del llibre. */
    private String isbn;
    /** Portada del llibre com a array de bytes. */
    private byte[] cover;
    /** Editorial del llibre. */
    private String editorial;
    /** Any de publicació. */
    private LocalDate yearPublication;

    /**
     * Construeix una nova instància de Book.
     * @param bookId identificador únic
     * @param title títol del llibre
     * @param author autor del llibre
     * @param isbn ISBN del llibre
     * @param cover portada en bytes
     * @param editorial editorial
     * @param yearPublication any de publicació
     */
    public Book(int bookId, String title, String author, String isbn, byte[] cover, String editorial,
            LocalDate yearPublication) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.cover = cover;
        this.editorial = editorial;
        this.yearPublication = yearPublication;
    }


    /**
     * Obté l'identificador del llibre.
     * @return identificador del llibre
     */
    public int getBookId() {
        return bookId;
    }
    /**
     * Estableix l'identificador del llibre.
     * @param bookId identificador del llibre
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    /**
     * Obté el títol del llibre.
     * @return títol
     */
    public String getTitle() {
        return title;
    }
    /**
     * Estableix el títol del llibre.
     * @param title títol
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Obté l'autor del llibre.
     * @return autor
     */
    public String getAuthor() {
        return author;
    }
    /**
     * Estableix l'autor del llibre.
     * @param author autor
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * Obté l'ISBN del llibre.
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }
    /**
     * Estableix l'ISBN del llibre.
     * @param isbn ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    /**
     * Obté la portada del llibre.
     * @return portada
     */
    public byte[] getCover() {
        return cover;
    }
    /**
     * Estableix la portada del llibre.
     * @param cover portada
     */
    public void setCover(byte[] cover) {
        this.cover = cover;
    }
    /**
     * Obté l'editorial del llibre.
     * @return editorial
     */
    public String getEditorial() {
        return editorial;
    }
    /**
     * Estableix l'editorial del llibre.
     * @param editorial editorial
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    /**
     * Obté l'any de publicació.
     * @return any de publicació
     */
    public LocalDate getYearPublication() {
        return yearPublication;
    }
    /**
     * Estableix l'any de publicació.
     * @param yearPublication any de publicació
     */
    public void setYearPublication(LocalDate yearPublication) {
        this.yearPublication = yearPublication;
    }
    /**
     * Comprova si el llibre està actualment en préstec.
     * @param loans llista de préstecs
     * @param bookLoans llista d'associacions llibre-préstec
     * @return true si el llibre està en préstec, false altrament
     */
    public boolean isOnLoan(List<Loan> loans, List<BookLoan> bookLoans) {
        for (BookLoan bl : bookLoans) {
            if (bl.getBookId() == this.bookId) {
                for (Loan loan : loans) {
                    if (loan.getLoanId() == bl.getLoanId() && "active".equalsIgnoreCase(loan.getStatus())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
