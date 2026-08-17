package com.airtribe.librarymanagement.entity;

import java.time.LocalDateTime;

public class Reservation {
    private String reservationId;
    private long patronId;
    private long bookId;
    private LocalDateTime createdAt;

    public Reservation(String reservationId, long patronId, long bookId) {
        this.reservationId = reservationId;
        this.patronId = patronId;
        this.bookId = bookId;
        this.createdAt = LocalDateTime.now();
    }

    /** 
     * @return String
     */
    public String getReservationId() {
        return reservationId;
    }

    /** 
     * @return long
     */
    public long getPatronId() {
        return patronId;
    }

    /** 
     * @return long
     */
    public long getBookId() {
        return bookId;
    }


    /** 
     * @return LocalDateTime
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "Reservation [reservationId=" + reservationId + ", patronId=" + patronId + ", bookId=" + bookId
                + ", createdAt=" + createdAt + "]";
    }
    
}
