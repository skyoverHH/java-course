package HomeWork4.dao;

import HomeWork4.model.User;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(String username) {
        String sql = "INSERT INTO users (username) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось создать запись User", e);
        }
    }

    public void update(Long id, String username) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить запись User", e);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить запись User", e);
        }
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new User(
                                rs.getLong("id"),
                                rs.getString("username")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить запись User по id", e);
        }
        return Optional.empty();
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                users.add(
                        new User(
                                rs.getLong("id"),
                                rs.getString("username")
                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить все записи User", e);
        }
        return users;
    }
}