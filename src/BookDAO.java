import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {
    private static final Logger logger = Logger.getLogger(BookDAO.class.getName());
    private final Connection connection;

    public BookDAO() {
        this.connection = Database.getConnection();
    }

    public void createBook(Book book) {
        String sql = "INSERT INTO books (isbn, title, author) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public Book readBook(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Book(resultSet.getString("title"), resultSet.getString("author"), isbn);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
        return null;
    }

    public void deleteBook(String isbn) {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                books.add(new Book(resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("isbn")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
        return books;
    }
}