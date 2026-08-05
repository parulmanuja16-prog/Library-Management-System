package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.entity.Book;
import com.airtribe.librarymanagement.exception.InputDataNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class InventoryService {
    private static InventoryService instance;

    private final List<Book> inventoryBooks = new CopyOnWriteArrayList<>();
    private static final Logger logger = Logger.getLogger(InventoryService.class.getName());
   
    private InventoryService() {
        // Private constructor to prevent instantiation
    }

    /** 
     * @return InventoryService
     */
    public static InventoryService getInstance() {
        if (instance == null) {
            synchronized (InventoryService.class) {
                if (instance == null) {
                    instance = new InventoryService();
                }
            }
        }
        return instance;
    }
    
    /** 
     * @param book
     */
    public void addBookInInventory(Book book) {
     inventoryBooks.add(book);
    }
    
   
    /** 
     * @param bookId
     */
    public void decreaseInventory(long bookId) {
        inventoryBooks.removeIf(book -> book.getBookId() == bookId);
    }
    /** 
     * @param bookId
     * @return Book
     */
    public Book getBookById(long bookId) {
        for (Book book : inventoryBooks) {
            if (book.getBookId() == bookId) {
                return book;
            }
        }
        return null;
    }

    /** 
     * @param bookId
     * @throws InputDataNotValidException
     */
    public void markBorrowed(long bookId) throws InputDataNotValidException {
        Book book = getBookById(bookId);
        if (book != null) {
            book.setBorrowed(true);
            logger.info("Book marked as borrowed: " + book);
        }else{
            throw new InputDataNotValidException("Book ID "+bookId+" is not valid.");
        }
    }
            
    /** 
     * @param bookId
     * @throws InputDataNotValidException
     */
    public void markReturned(long bookId) throws InputDataNotValidException {
        Book book = getBookById(bookId);
        if (book != null) {
            book.setBorrowed(false);
            logger.info("Book marked as returned: " + book);
        }else{
            throw new InputDataNotValidException("Book ID "+bookId+" is not valid.");
        }
    }
    /** 
     * @param bookId
     * @return boolean
     * @throws InputDataNotValidException
     */
    public boolean isBorrowed(long bookId) throws InputDataNotValidException {
       Book book = getBookById(bookId);
        if (book != null) {
            logger.info("Checking borrow status for book ID " + bookId + ": " + book);
            return book.isBorrowed();
        } else {
            throw new InputDataNotValidException("Book ID "+bookId+" is not valid.");
        }
    }

    /** 
     * @param branchId
     * @return List<Book>
     */
    public List<Book> getAvailableBooks(String branchId) {
        logger.info("Fetching available books for branch: " + branchId);

        List<Book> availableBooks = new ArrayList<>();
        for (Book book : inventoryBooks) {
            if (!book.isBorrowed()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    
    }

    /** 
     * @param branchId
     * @return List<Book>
     */
    public List<Book> getAllBorrowedBooks(String branchId) {
       
        List<Book> borrowedBooks = new ArrayList<>();
        for (Book book : inventoryBooks) {
            if (book.isBorrowed()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }
     /** 
      * @return List<Book>
      */
     public List<Book> getInventoryBooks() {
        return inventoryBooks;
    }

}
