import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;


public class LoanDAO {

    private static final Logger logger = Logger.getLogger(LoanDAO.class.getName());
    private final Connection connection;

    public LoanDAO() {
        this.connection = Database.getConnection();
    }

    public void createLoan(Loan loan) {
        String sql = "INSERT INTO loans (user_id, book_isbn, issue_date, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, loan.getMember().getId());
            statement.setString(2, loan.getBook().getIsbn());
            statement.setDate(3, new Date(loan.getIssueDate().getTime()));
            statement.setDate(4, new Date(loan.getDueDate().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public void deleteLoan(int userId, String bookIsbn) {
        String sql = "DELETE FROM loans WHERE user_id = ? AND book_isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, bookIsbn);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                UserDAO userDAO = new UserDAO();
                BookDAO bookDAO = new BookDAO();
                User user = userDAO.readUser(resultSet.getInt("user_id"));
                Book book = bookDAO.readBook(resultSet.getString("book_isbn"));
                loans.add(new Loan(resultSet.getInt("id"), book, (Member) user, resultSet.getDate("issue_date"), resultSet.getDate("due_date")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
        return loans;
    }

    public List<Loan> getLoansByMember(Member member) {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, member.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookDAO bookDAO = new BookDAO();
                Book book = bookDAO.readBook(resultSet.getString("book_isbn"));
                loans.add(new Loan(resultSet.getInt("id"), book, member, resultSet.getDate("issue_date"), resultSet.getDate("due_date")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
        return loans;
    }
}