import java.util.*;

public class Library {
    private final Catalog catalog;
    private final Map<Integer, User> users;
    private final Map<User, List<Loan>> loans;

    public Library() {
        catalog = new Catalog();
        users = new HashMap<>();
        loans = new HashMap<>();
    }

    public void addBook(Book book) {
        catalog.addBook(book);
    }

    public void removeBook(Book book) {
        catalog.removeBook(book);
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public void borrowBook(Member member, Book book) {
        if (catalog.getBooks().contains(book)) {
            catalog.removeBook(book);
            Loan loan = new Loan(book, member, new Date(), new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)); // Due in 1 week
            loans.computeIfAbsent(member, k -> new ArrayList<>()).add(loan);
        }
    }

    public void returnBook(Member member, Book book) {
        List<Loan> memberLoans = loans.get(member);
        if (memberLoans != null) {
            memberLoans.removeIf(loan -> loan.getBook().equals(book));
            catalog.addBook(book);
        }
    }

    public List<Loan> getLoansByMember(Member member) {
        return loans.getOrDefault(member, Collections.emptyList());
    }

    public SortedSet<Book> getAvailableBooks() {
        return catalog.getBooks();
    }

    public User getUserById(int id) {
        return users.get(id);
    }
}