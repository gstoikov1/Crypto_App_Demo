package com.example.tradingappdemo.repository.transaction;

import com.example.tradingappdemo.model.Transaction;
import com.example.tradingappdemo.model.TransactionType;
import com.example.tradingappdemo.repository.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.tradingappdemo.logging.ExceptionLogger.logSQLException;

@Repository
public class TransactionRepositoryImplementation implements TransactionRepository {
    private final DbConfig dbConfig;

    @Autowired
    public TransactionRepositoryImplementation(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public void save(Transaction transaction) {
        saveTransaction(transaction);
    }

    @Override
    public List<Transaction> findAllTransactionsByUserId(int userId) {
        String sql = "SELECT id, user_id, symbol, price, quantity, trade_time, type FROM transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = Transaction.builder()
                        .id(rs.getInt("id"))
                        .userID(rs.getInt("user_id"))
                        .symbol(rs.getString("symbol"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getBigDecimal("quantity"))
                        .tradeTime(rs.getTimestamp("trade_time").toLocalDateTime())
                        .transactionType(TransactionType.valueOf(rs.getString("type")))
                        .build();
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            logSQLException(e);
        }

        return transactions;
    }

    @Override
    public void deleteAllTransactionsByUserId(int userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    private void saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, symbol, price, quantity, trade_time,type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getUserID());
            stmt.setString(2, transaction.getSymbol());
            stmt.setBigDecimal(3, transaction.getPrice());
            stmt.setBigDecimal(4, transaction.getQuantity());
            stmt.setTimestamp(5, Timestamp.valueOf(transaction.getTradeTime()));
            stmt.setString(6, transaction.getTransactionType().toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e);
        }
    }

}
