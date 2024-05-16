import java.util.*;

public class Catalog {
    private final SortedSet<Book> books;

    public Catalog() {
        books = new TreeSet<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public SortedSet<Book> getBooks() {
        return books;
    }
}