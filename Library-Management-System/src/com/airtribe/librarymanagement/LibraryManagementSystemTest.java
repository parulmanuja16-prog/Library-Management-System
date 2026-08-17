package com.airtribe.librarymanagement;

import java.util.List;
import java.util.logging.Logger;

import com.airtribe.librarymanagement.entity.Book;
import com.airtribe.librarymanagement.entity.BorrowedBookRecord;
import com.airtribe.librarymanagement.entity.Patron;
import com.airtribe.librarymanagement.exception.InputDataNotValidException;
import com.airtribe.librarymanagement.service.BookService;
import com.airtribe.librarymanagement.service.InventoryService;
import com.airtribe.librarymanagement.service.LendingService;
import com.airtribe.librarymanagement.service.PatronService;
import com.airtribe.librarymanagement.service.ReservationService;
import com.airtribe.librarymanagement.util.DateUtil;

public class LibraryManagementSystemTest {
    private static final Logger logger = Logger.getLogger(LibraryManagementSystemTest.class.getName());
    public static void main(String[] args) {
        BookService bookService = BookService.getInstance();
        PatronService patronService = PatronService.getInstance();
        InventoryService inventoryService = InventoryService.getInstance();
        LendingService lendingService = LendingService.getInstance();
        ReservationService reservationService = ReservationService.getInstance();
        System.out.println("***********************************Library management system initialized.**************************************8");
        try {
            Book book1 = bookService.addBook("Clean Code", "Robert C. Martin", "9780132350884", "2008", "Programming");
            inventoryService.addBookInInventory(book1);
           
            Book book2 = bookService.addBook("Java programming", "Head First", "32534546", "2008", "Programming");
            inventoryService.addBookInInventory(book2);
            
            Book book3 = bookService.addBook("Design Patterns", "Gang of Four", "246757", "2008", "Programming");
            inventoryService.addBookInInventory(book3);
          
            Book book4 = bookService.addBook("Harry Potter", "J.K. Rowling", "246757", "2008", "Fiction");
            inventoryService.addBookInInventory(book4);
           
            Book book5 = bookService.addBook("Message in a Bottle", "Sarah Dessen", "246757", "2008", "Fiction");
            inventoryService.addBookInInventory(book5);

            System.out.println(".......................Search books by title.................");
            List<Book> titleSearchResults = bookService.search("title", "Clean Code");
            for (Book book : titleSearchResults) {
                System.out.println("Found by title: " + book);
            }
            if (titleSearchResults.isEmpty()) {
                System.out.println("No books found by title 'Clean Code'.");
            }

            System.out.println(".......................Search books by author.................");
            List<Book> authorSearchResults = bookService.search("author", "J.K. Rowling");
            for (Book book : authorSearchResults) {
                System.out.println("Found by author: " + book);
            }
            if (authorSearchResults.isEmpty()) {
                System.out.println("No books found by author 'J.K. Rowling'.");
            }

            System.out.println(".......................Search books by ISBN.................");
            List<Book> isbnSearchResults = bookService.search("isbn", "32534546");
            for (Book book : isbnSearchResults) {
                System.out.println("Found by ISBN: " + book);
            }
            if (isbnSearchResults.isEmpty()) {
                System.out.println("No books found by ISBN '32534546'.");
            }

            Patron patron1 = patronService.addPatron("Asha", 9876543210L, "asha@example.com", "Programming, Fiction");            
            Patron patron2 = patronService.addPatron("Parul", 9716569714L, "parul@example.com", "Fiction");

            System.out.println(".......................Update patron details.................");
            Patron updatedPatron = patronService.updatePatron(patron2.getPatronId(), "Parul Sharma", 9716569715L, "parul.sharma@example.com");
            System.out.println("Updated Patron: " + updatedPatron.getName() + ", mobile=" + updatedPatron.getMobileNumber() + ", email=" + updatedPatron.getEmail());

            logger.info("___________________________________________________________________________________________________________");
            System.out.println(".......................Lending books.................");

            lendingService.lendBook(patron1.getPatronId(), book1.getBookId());            
            
            lendingService.lendBook(patron1.getPatronId(), book3.getBookId());
            lendingService.lendBook(patron2.getPatronId(), book1.getBookId()); // This should fail since book1 is already lent to patron1
            lendingService.lendBook(patron2.getPatronId(), book4.getBookId());
            lendingService.lendBook(patron2.getPatronId(), book5.getBookId());

            System.out.println(".......................Returning books.................");
            lendingService.returnBook(book1.getBookId(), patron1.getPatronId());

            System.out.println(".......................Get Borrowed Books History for Patron1 .................");
            List<BorrowedBookRecord> borrowedBooks = lendingService.getBorrowedBooksByPatron(patron1.getPatronId());
            for (BorrowedBookRecord record : borrowedBooks) {
                 System.out.println("Borrowed book record found for patron " + patron1.getName() + ": " 
                 + inventoryService.getBookById(record.getBookId()).getTitle()
                + " (Borrowed on: " + DateUtil.dateToString(record.getIssuedDate()) + " Returned: " + record.isReturned() 
                +" Returned on: " + DateUtil.dateToString(record.getReturnDate()) + ")");
            }

            System.out.println(".......................Get Recommendations Based on Preferences and Borrowed History for Patron1 .................");
            List<Book> recommendations = patronService.recommendBooks(patron1);
            for (Book book : recommendations) {
                System.out.println("Recommended Book: " + book);
            }

            System.out.println(".......................Observer Pattern Implementation while creating Reservations.................");
            book5.addObserver(patron1);
            ReservationService.getInstance().createReservation(patron1.getPatronId(), book5.getBookId());
            logger.info("Patron " + patron1.getName() + "created reservationf for book: " + book5.getTitle());
          
            lendingService.returnBook(book5.getBookId(), patron2.getPatronId());
            logger.info(patron2.getName() + " returned the book: " + book5.getTitle());
        } catch (InputDataNotValidException e) {
            logger.severe("Error occurred while lending book: " + e.getMessage());
        }

        logger.info("Library management features verified.");
    }
}

