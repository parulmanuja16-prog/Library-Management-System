package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.entity.Book;
import com.airtribe.librarymanagement.exception.InputDataNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookService {
    private static BookService instance;
    private InventoryService inventoryService = InventoryService.getInstance();
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private BookService() {
        // Private constructor to prevent instantiation
    }
    
    /** 
     * @return BookService
     */
    public static BookService getInstance() {
        if (instance == null) {
            synchronized (BookService.class) {
                if (instance == null) {
                    instance = new BookService();
                }
            }
        }
        return instance;
    }

    /** 
     * @param title
     * @param author
     * @param isbn
     * @param publicationYear
     * @param genre
     * @return Book
     */
    public Book addBook(String title, String author, String isbn, String publicationYear, String genre) {
        Book book = new Book(title, author, isbn, publicationYear, genre);
       
        getBooksList().add(book);       
        logger.info("Book added: " + book); 
        return book;
    }

     /** 
      * @param bookID
      * @return Book
      * @throws InputDataNotValidException
      */
     public Book removeBook(long bookID) throws InputDataNotValidException {         
        Book book = inventoryService.getBookById(bookID);
        if(book == null){
            throw new InputDataNotValidException("Book ID "+bookID+" is not valid.");
        }   
        List<Book> list = getBooksList();      
        list.remove(book);   
        logger.info("Book removed: " + book);
        return book;     
    }

    /** 
     * @return List<Book>
     */
    private List<Book> getBooksList() {
        return inventoryService.getInventoryBooks();
    }

    /** 
     * @param criteria
     * @param searchVal
     * @return List<Book>
     * @throws InputDataNotValidException
     */
    public List<Book> search(String criteria, String searchVal) throws InputDataNotValidException{
        switch (criteria){
            case "title":
                return searchByTitle(searchVal);
            case "author":
                return searchByAuthor(searchVal);
            case "isbn":
                return searchByISBN(searchVal);
            default:
               throw new InputDataNotValidException("Invalid search criteria: " + criteria);
        }
    }

   /** 
    * @param searchVal
    * @return List<Book>
    */
   public List<Book> searchByTitle(String searchVal){
        List<Book> results = new ArrayList<>();
        logger.info("Searching books by title: " + searchVal);        
        for(Book book : getBooksList()){
            if(book.getTitle().equalsIgnoreCase(searchVal)){
                results.add(book);
                logger.info("Book found by title " + searchVal + ": " + book);
            }
        }
        return results;
    }
    /** 
     * @param searchVal
     * @return List<Book>
     */
    public List<Book> searchByAuthor(String searchVal){
        List<Book> results = new ArrayList<>();
        logger.info("Searching books by author: " + searchVal);
        for(Book book : getBooksList()){
            if(book.getAuthor().equalsIgnoreCase(searchVal)){
                results.add(book);
                logger.info("Book found by author " + searchVal + ": " + book);
            }
        }
        
        return results;
    }
    /** 
     * @param searchVal
     * @return List<Book>
     */
    public List<Book> searchByISBN(String searchVal){
        List<Book> results = new ArrayList<>();
        logger.info("Searching books by ISBN: " + searchVal);
        for(Book book : getBooksList()){
            if(book.getIsbn().equalsIgnoreCase(searchVal)){
                results.add(book);
                logger.info("Book found by ISBN " + searchVal + ": " + book);
            }
        }
        return results;
    }

    

    /** 
     * @param bookId
     * @param updatedBook
     * @return Book
     * @throws InputDataNotValidException
     */
    public Book updateBook(long bookId, Book updatedBook) throws InputDataNotValidException {
        Book existingBook = inventoryService.getBookById(bookId);
        if(existingBook == null){
            throw new InputDataNotValidException("Book ID "+bookId+" is not valid.");
        }
        List<Book> list = getBooksList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBookId() == bookId) {
                list.set(i, updatedBook);
                return updatedBook;
            }
        }
        throw new InputDataNotValidException("Failed to update book with ID " + bookId);
    }
   
}
