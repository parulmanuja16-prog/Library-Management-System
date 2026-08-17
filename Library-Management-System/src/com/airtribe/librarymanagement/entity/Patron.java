package com.airtribe.librarymanagement.entity;

import java.util.concurrent.atomic.AtomicLong;

import com.airtribe.librarymanagement.observer.PatronObserver;

public class Patron implements PatronObserver {
    private long patronId;
    private String name;
    private long mobileNumber;    
    private String email;
    private String preferredGenres = "";

    private static AtomicLong idCounter = new AtomicLong(0);

    public Patron( String name, long mobileNumber, String email, String preferredGenres) {
        this.patronId = idCounter.incrementAndGet();
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.preferredGenres = preferredGenres;
    }
    
    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * @return long
     */
    public long getMobileNumber() {
        return mobileNumber;
    }

    /** 
     * @param mobileNumber
     */
    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    

    /** 
     * @return long
     */
    public long getPatronId() {
        return patronId;
    }

    /** 
     * @return String
     */
    public String getName() {
        return name;
    }

   
    /** 
     * @return String
     */
    public String getPreferredGenres() {
        return preferredGenres;
    }

    /** 
     * @param genre
     */
    public void addPreferredGenre(String genre) {
        if (genre != null && !genre.isBlank()) {
            if (!preferredGenres.isEmpty()) {
                preferredGenres += ", ";
            }
            preferredGenres += genre.trim().toLowerCase();
        }
    }


    /** 
     * @param bookId
     * @param title
     * @param status
     */
    @Override
    public void update(long bookId, String title, String status) {
        System.out.println("Patron " + name + " notified about book " + title + " status: " + status);
    }

    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Patron [patronId=" + patronId + ", name=" + name + ", mobileNumber=" + mobileNumber + ", email=" + email
                + ", preferredGenres=" + preferredGenres + "]";
    }
    
}
