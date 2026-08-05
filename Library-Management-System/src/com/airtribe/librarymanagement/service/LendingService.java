package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.entity.BorrowedBookRecord;
import com.airtribe.librarymanagement.entity.Patron;
import com.airtribe.librarymanagement.exception.InputDataNotValidException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class LendingService {
    private static LendingService instance;
    private static final Logger logger = Logger.getLogger(LendingService.class.getName());
     //This map will store the borrowed book records history with the issueId as the key
    private final Map<Long, BorrowedBookRecord> borrowedBooks = new LinkedHashMap<>();
    private InventoryService inventoryService = InventoryService.getInstance();
    private PatronService patronService = PatronService.getInstance();

    private LendingService() {
        // Private constructor to prevent instantiation
    }
    /** 
     * @return LendingService
     */
    public static LendingService getInstance() {
        if (instance == null) {
            synchronized (LendingService.class) {
                if (instance == null) {
                    instance = new LendingService();
                }
            }
        }
        return instance;
    }
   
    /** 
     * @param patronId
     * @param bookId
     * @return boolean
     * @throws InputDataNotValidException
     */
    public boolean lendBook(long patronId, long bookId) throws InputDataNotValidException {
        if (inventoryService.isBorrowed(bookId)) {
            logger.info("Book " + bookId + " is already borrowed.");
            return false; // Book is already borrowed
        }
        inventoryService.markBorrowed( bookId);
        BorrowedBookRecord issuedBook = new BorrowedBookRecord(patronId, bookId, java.time.LocalDateTime.now());
        borrowedBooks.put(issuedBook.getIssueId(), issuedBook);
        logger.info("Book " + bookId + " lent successfully to patron " + patronId);
        return true;
    }

    /** 
     * @param bookId
     * @param patronId
     * @return boolean
     * @throws InputDataNotValidException
     */
    public boolean returnBook(long bookId, long patronId) throws InputDataNotValidException {
        // Find the borrowed book record by its ID
        BorrowedBookRecord record = null;
        for (BorrowedBookRecord r : borrowedBooks.values()) {
            if (r.getBookId() == bookId && r.getPatronId() == patronId) {
                record = r;
                break;
            }
        }
        if(record == null){
            throw new InputDataNotValidException("No borrowed book record found for the given book ID: " + bookId);
            
        }
        if (record != null && !record.isReturned()) {
            record.setReturned(true);
            inventoryService.getInventoryBooks().stream()
                    .filter(book -> book.getBookId() == bookId)
                    .findFirst()
                    .ifPresent(book -> book.setBorrowed(false));    
            record.setReturnDate(java.time.LocalDateTime.now());
           // logger.info("Book " + bookId + " returned successfully by patron " + record.getPatronId());
            return true;
        }
        
        return false; // No active loan found for this patron and book
    }

    /** 
     * @return List<BorrowedBookRecord>
     */
    public List<BorrowedBookRecord> listLoans() {
        return new ArrayList<>(borrowedBooks.values());
    }


    /** 
     * @param patronId
     * @return List<BorrowedBookRecord>
     * @throws InputDataNotValidException
     */
    public List<BorrowedBookRecord> getBorrowedBooksByPatron(long patronId) throws InputDataNotValidException {
        Patron patron = patronService.getPatron(patronId); // Validate patron existence
        if(patron == null){
            throw new InputDataNotValidException("No patron found with the given ID: " + patronId);
        }
        List<BorrowedBookRecord> patronBorrowedBooks = new ArrayList<>();
        for (BorrowedBookRecord record : borrowedBooks.values()) {
            if (record.getPatronId() == patronId) {
                patronBorrowedBooks.add(record);

            }
        }
        return patronBorrowedBooks;
    }

}
