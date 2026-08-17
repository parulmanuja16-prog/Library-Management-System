package com.airtribe.librarymanagement.observer;

public interface BookObserver {

    void update(long bookId, String title, String status);

}
