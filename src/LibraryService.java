import java.util.List;

public class LibraryService {
    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final LoanDAO loanDAO;
    private final AuditService auditService;

    public LibraryService() {
        this.userDAO = new UserDAO();
        this.bookDAO = new BookDAO();
        this.loanDAO = new LoanDAO();
        this.auditService = new AuditService();
    }

    public void addBook(Book book) {
        bookDAO.createBook(book);
        auditService.logAction("addBook");
    }

    public void removeBook(Book book) {
        bookDAO.deleteBook(book.getIsbn());
        auditService.logAction("removeBook");
    }

    public void registerUser(User user) {
        userDAO.createUser(user);
        auditService.logAction("registerUser");
    }

    public void borrowBook(Member member, Book book) {
        Loan loan = new Loan(book, member, new java.util.Date(), new java.util.Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
        loanDAO.createLoan(loan);
        auditService.logAction("borrowBook");
    }

    public void returnBook(Member member, Book book) {
        List<Loan> loans = loanDAO.getAllLoans();
        for (Loan loan : loans) {
            if (loan.getBook().equals(book) && loan.getMember().equals(member)) {
                int idu = loan.getMember().getId();
                String ibu = loan.getBook().getIsbn();
                loanDAO.deleteLoan(idu, ibu);
                bookDAO.createBook(book);
                auditService.logAction("returnBook");
                break;
            }
        }
    }

    public List<Loan> getLoansByMember(Member member) {
        auditService.logAction("getLoansByMember");
        return loanDAO.getLoansByMember(member);
    }

    public List<Book> getAvailableBooks() {
        auditService.logAction("getAvailableBooks");
        List<Book> allBooks = bookDAO.getAllBooks();
        List<Loan> allLoans = loanDAO.getAllLoans();
        for (Loan loan : allLoans) {
            allBooks.remove(loan.getBook());
        }
        return allBooks;
    }

    public User getUserById(int id) {
        auditService.logAction("getUserById");
        return userDAO.readUser(id);
    }

    public List<User> getAllUsers() {
        auditService.logAction("getAllUsers");
        return userDAO.getAllUsers();
    }
}