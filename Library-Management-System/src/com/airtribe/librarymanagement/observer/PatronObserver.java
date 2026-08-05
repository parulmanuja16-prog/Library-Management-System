package com.airtribe.librarymanagement.observer;

public interface PatronObserver extends BookObserver {
    void update(long bookId, String title, String status);

}
