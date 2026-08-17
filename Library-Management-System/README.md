# Library-Management-System

This system helps librarians manage books, patrons, inventory, and the lending process efficiently.

## Project Functionalities

1. Book Management
   - Implements a `Book` class with attributes such as title, author, ISBN, and publication year.
   - Supports adding, removing, and updating books in the library inventory.
   - Provides search functionality to find books by title, author, or ISBN.

2. Patron Management
   - Defines a `Patron` class to represent library members.
   - Supports adding new patrons and updating patron information.
   - Tracks patron borrowing history.

3. Lending Process
   - Implements book checkout and return functionality.

4. Inventory Management
   - Tracks available books and borrowed books.

5. Reservation System
   - Allows patrons to reserve books that are currently checked out.
   - Implements a notification system for when reserved books become available.

6. Recommendation System
   - Implements a book recommendation system based on patron borrowing history and preferences.
   - Uses appropriate data structures and algorithms to efficiently generate recommendations.

## Class Diagram

Below is the class diagram representing the main domain entities and their relationships.

![Class Diagram](./class-diagram.svg)
