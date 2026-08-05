package com.airtribe.librarymanagement.service;

import com.airtribe.librarymanagement.entity.Reservation;
import com.airtribe.librarymanagement.exception.InputDataNotValidException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private static ReservationService instance;
    
    private final Map<String, Reservation> reservations = new LinkedHashMap<>();
    private InventoryService inventoryService = InventoryService.getInstance();

    private ReservationService() {
        // Private constructor to prevent instantiation
    }

    /** 
     * @return ReservationService
     */
    public static ReservationService getInstance() {
        if (instance == null) {
            synchronized (ReservationService.class) {
                if (instance == null) {
                    instance = new ReservationService();
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
    public boolean createReservation(long patronId, long bookId) throws InputDataNotValidException {
        if (inventoryService.isBorrowed(bookId)) {
            reservations.put(patronId + ":" + bookId, new Reservation("R-" + reservations.size(), patronId, bookId));
            inventoryService.getBookById(bookId).addObserver(PatronService.getInstance().getPatron(patronId));
            return true;
        }
        return false;
    }

    /** 
     * @return List<Reservation>
     */
    public List<Reservation> listReservations() {
        return new ArrayList<>(reservations.values());
    }
}
