package com.airtribe.librarymanagement.entity;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class BorrowedBookRecord {
    private long issueId;
    private long patronId;
    private long bookId;
    private LocalDateTime issuedDate;
    private LocalDateTime returnDate;    
    private boolean isReturned;
    private static AtomicLong issueIdCounter = new AtomicLong(0);

    public BorrowedBookRecord(long patronId, long bookId, LocalDateTime issuedDate) {
        this.issueId = issueIdCounter.incrementAndGet();
        this.patronId = patronId;
        this.bookId = bookId;
        this.issuedDate = issuedDate;
        this.isReturned = false;
    }

    /** 
     * @return long
     */
    public long getIssueId() {
        return issueId;
    }

    /** 
     * @return long
     */
    public long getPatronId() {
        return patronId;
    }    

    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getReturnDate() {
        return returnDate;
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
     * @param patronId
     */
    public void setPatronId(long patronId) {
        this.patronId = patronId;
    }
    
    /** 
     * @param issuedDate
     */
    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }
    /** 
     * @param returnDate
     */
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    /** 
     * @return boolean
     */
    public boolean isReturned() {
        return isReturned;
    }

    /** 
     * @param isReturned
     */
    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }

    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "BorrowedBookRecord [issueId=" + issueId + ", patronId=" + patronId + ", bookId=" + bookId
                + ", issuedDate=" + issuedDate + ", returnDate=" + returnDate + ", isReturned=" + isReturned + "]";
    }
   


    
}