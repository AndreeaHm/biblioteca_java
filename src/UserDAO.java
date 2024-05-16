import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());
    private final Connection connection;

    public UserDAO() {
        this.connection = Database.getConnection();
    }

    public void createUser(User user) {
        String sql = "INSERT INTO users (name, username, password, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user instanceof Staff ? "staff" : "member");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public User readUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                if (type.equals("staff")) {
                    return new Staff(resultSet.getString("name"), id, resultSet.getString("username"), resultSet.getString("password"), "Librarian");
                } else {
                    return new Member(resultSet.getString("name"), id, resultSet.getString("username"), resultSet.getString("password"), "Gold");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
        return null;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, username = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                if (type.equals("staff")) {
                    users.add(new Staff(name, id, username, password, "Librarian"));
                } else {
                    users.add(new Member(name, id, username, password, "Gold"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error", e);
        }
        return users;
    }
}