package com.airtribe.librarymanagement.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.airtribe.librarymanagement.observer.BookObserver;

public class Book {
    private long bookId;
    private String title;
    private String author;
    private String isbn;
    private String publicationYear;
    private String genre;
    private boolean isBorrowed;
    List<BookObserver> observers = new ArrayList<>();
    

    private static AtomicLong idCounter = new AtomicLong(0);
    public Book(String title, String author, String isbn, String publicationYear, String genre) {
        this.bookId = idCounter.incrementAndGet();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
        
        this.isBorrowed = false;
    }


    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", isbn=" + isbn
                + ", publicationYear=" + publicationYear + ", genre=" + genre + ", isBorrowed=" + isBorrowed + "]";
    }    
   
    /** 
     * @return long
     */
    public long getBookId() {
        return bookId;
    }
    /** 
     * @param bookId
     */
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /** 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /** 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    /** 
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** 
     * @return String
     */
    public String getIsbn() {
        return isbn;
    }

    /** 
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /** 
     * @return String
     */
    public String getPublicationYear() {
        return publicationYear;
    }

    /** 
     * @param publicationYear
     */
    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    /** 
     * @return String
     */
    public String getGenre() {
        return genre;
    }

    /** 
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /** 
     * @return boolean
     */
    public boolean isBorrowed() {
        return isBorrowed;
    }

    /** 
     * @param isBorrowed
     */
    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
        if(!isBorrowed) {
            // Notify observers that the book is now available
            for (BookObserver observer : observers) {
                observer.update(this.bookId, this.title,"AVAILABLE");
            }
        } else {
            // Notify observers that the book is now borrowed
            for (BookObserver observer : observers) {
                observer.update(this.bookId, this.title,"BORROWED");
            }
        }
    }


    public void addObserver(Patron patron) {
        observers.add(patron);
        
    }

}