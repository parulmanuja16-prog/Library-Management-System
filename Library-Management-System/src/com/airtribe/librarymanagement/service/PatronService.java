package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.entity.Book;
import com.airtribe.librarymanagement.entity.Patron;
import com.airtribe.librarymanagement.exception.InputDataNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class PatronService {
    private static PatronService instance;
    private CopyOnWriteArrayList<Patron> patrons = new CopyOnWriteArrayList<>();
    private InventoryService inventoryService = InventoryService.getInstance();
    private static final Logger logger = Logger.getLogger(PatronService.class.getName());
    private PatronService() {
        // Private constructor to prevent instantiation
    }
    /** 
     * @return PatronService
     */
    public static PatronService getInstance() {
        if (instance == null) {
            synchronized (PatronService.class) {
                if (instance == null) {
                    instance = new PatronService();
                }
            }
        }
        return instance;
    }
   
    /** 
     * @param name
     * @param mobileNumber
     * @param email
     * @param preferredGenres
     * @return Patron
     */
    public Patron addPatron(String name, long mobileNumber, String email, String preferredGenres) {
        Patron patron = new Patron(name, mobileNumber, email, preferredGenres);
        patrons.add(patron);
        logger.info("Patron added: " + patron);
        return patron;
    }

    /** 
     * @param patronId
     * @return Patron
     * @throws InputDataNotValidException
     */
    public Patron getPatron(long patronId) throws InputDataNotValidException {
        for (Patron patron : patrons) {
            if (patron.getPatronId() == patronId) {
                logger.info("Patron found by ID " + patronId + ": " + patron);
                return patron;
            }
        }
        throw new InputDataNotValidException("Patron ID " + patronId + " is not valid.");
    }
    /** 
     * @param patronId
     * @param name
     * @param mobileNumber
     * @param email
     * @return Patron
     * @throws InputDataNotValidException
     */
    public Patron updatePatron(long patronId, String name, long mobileNumber, String email) throws InputDataNotValidException {
        for (Patron patron : patrons) {
            if (patron.getPatronId() == patronId) {
                patron.setName(name);
                patron.setMobileNumber(mobileNumber);
                patron.setEmail(email);
                logger.info("Patron updated: " + patron);
                return patron;
            }
        }
        throw new InputDataNotValidException("Patron ID " + patronId + " is not valid.");
    }

    /** 
     * @return List<Patron>
     */
    public List<Patron> listPatrons() {
        return patrons;
    }

    /** 
     * @param patron
     * @return List<Book>
     */
    public List<Book> recommendBooks(Patron patron) {
        List<Book> recommendations = new ArrayList<>();
     
        for (Book book : inventoryService.getInventoryBooks()) {
            if (patron.getPreferredGenres().contains(book.getGenre())) {
                if(!recommendations.contains(book)) {
                    recommendations.add(book);
                }
            }
        }
        return recommendations;
    }
}
