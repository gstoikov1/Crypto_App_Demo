package com.example.tradingappdemo.repository.user;

import com.example.tradingappdemo.exception.SuchEmailAlreadyExistsException;
import com.example.tradingappdemo.exception.SuchUsernameAlreadyExistsException;
import com.example.tradingappdemo.model.User;
import com.example.tradingappdemo.repository.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.tradingappdemo.logging.ExceptionLogger.EXCEPTION_LOGGING_FILE;
import static com.example.tradingappdemo.logging.ExceptionLogger.logSQLException;

@Repository
public class UserRepositoryImplementation implements UserRepository {

    private static final BigDecimal DEFAULT_FUNDS = BigDecimal.valueOf(10000);

    private final DbConfig dbConfig;

    @Autowired
    public UserRepositoryImplementation(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user must not be null");
        }

        if (findByUsername(user.getUsername()).isPresent()) {
            throw new SuchUsernameAlreadyExistsException("Username: " + user.getUsername() + " already exists.");
        }

        if (findByEmail(user.getEmail()).isPresent()) {
            throw new SuchEmailAlreadyExistsException("Email: " + user.getEmail() + " already exists.");
        }

        return saveUser(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username should not be null or blank");
        }
        User filter = User.builder().username(username).build();
        return Optional.ofNullable(getFirstUserOrNull(getUsersByFilter(filter)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email should not be null or blank");
        }
        User filter = User.builder().email(email).build();
        return Optional.ofNullable(getFirstUserOrNull(getUsersByFilter(filter)));
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username should not be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password should not be null or blank");
        }
        User filter = User.builder().username(username).password(password).build();
        return Optional.ofNullable(getFirstUserOrNull(getUsersByFilter(filter)));
    }

    @Override
    public Optional<User> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id should be a positive number");
        }
        User filter = User.builder().id(id).build();
        return Optional.ofNullable(getFirstUserOrNull(getUsersByFilter(filter)));
    }

    @Override
    public List<User> findAll() {
        return getUsersByFilter(null);
    }

    @Override
    public void updateUserFundsByUserId(int userId, BigDecimal newFunds) {

        String sql = "UPDATE users SET funds = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, newFunds);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e);
        }

    }

    @Override
    public void resetUserFundsToDefaultByUserId(int userId) {
        updateUserFundsByUserId(userId, DEFAULT_FUNDS);
    }

    private List<User> getUsersByFilter(User filter) {
        String sql = buildSelectQueryFromUser(filter);
        List<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (filter != null) {
                if (filter.getId() != 0) {
                    stmt.setInt(paramIndex++, filter.getId());
                }
                if (filter.getUsername() != null) {
                    stmt.setString(paramIndex++, filter.getUsername());
                }
                if (filter.getEmail() != null) {
                    stmt.setString(paramIndex++, filter.getEmail());
                }
                if (filter.getPassword() != null) {
                    stmt.setString(paramIndex++, filter.getPassword());
                }
            }
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(buildUserFromResultSet(rs));
            }
            rs.close();

        } catch (SQLException e) {
            logSQLException(e);
        }

        return users;
    }

    private User buildUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username1 = rs.getString("username");
        String email = rs.getString("email");
        String password = rs.getString("password_hash");
        Timestamp createdAt = rs.getTimestamp("created_at");
        BigDecimal funds = rs.getBigDecimal("funds");
        User user = User.builder()
                .id(id)
                .username(username1)
                .email(email)
                .password(password)
                .createdAt(createdAt.toLocalDateTime())
                .funds(funds).build();
        return user;
    }

    private String buildSelectQueryFromUser(User model) {
        StringBuilder sb = new StringBuilder("SELECT id, username, password_hash, email, created_at, funds FROM users");
        if (model == null) {
            return sb.toString();
        }

        List<String> conditions = new ArrayList<>();

        if (model.getUsername() != null) {
            conditions.add("username = ?");
        }
        if (model.getEmail() != null) {
            conditions.add("email = ?");
        }
        if (model.getPassword() != null) {
            conditions.add("password_hash = ?");
        }
        if (!conditions.isEmpty()) {
            sb.append(" WHERE ");
            sb.append(String.join(" AND ", conditions));
        }

        return sb.toString();
    }

    private User getFirstUserOrNull(List<User> users) {
        return users.isEmpty() ? null : users.get(0);
    }

    private User saveUser(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, created_at, funds) VALUES (?, ?, ?, ?, ?)";
        User savedUser = null;
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setBigDecimal(5, DEFAULT_FUNDS);

            stmt.executeUpdate();
            savedUser = findByUsername(user.getUsername()).orElse(null);
        } catch (SQLException e) {
            logSQLException(e);
        }
        return savedUser;
    }

}
