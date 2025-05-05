package com.example.tradingappdemo.repository.wallet;

import com.example.tradingappdemo.model.Wallet;
import com.example.tradingappdemo.repository.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.tradingappdemo.logging.ExceptionLogger.logSQLException;

@Repository
public class WalletRepositoryImplementation implements WalletRepository {
    private final DbConfig dbConfig;

    @Autowired
    public WalletRepositoryImplementation(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public Wallet save(Wallet wallet) {
        Wallet savedWallet = saveWallet(wallet);
        return wallet;
    }

    private Wallet saveWallet(Wallet wallet) {
        String sql = "INSERT INTO wallets (holder) VALUES (?)";
        Wallet savedWallet = null;
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wallet.getUserID());
            stmt.executeUpdate();
            findByUserId(wallet.getUserID());


        } catch (SQLException e) {
            logSQLException(e);
        }
        return wallet;
    }

    public Wallet findByUserId(int id) {
        String sql = "SELECT id, holder FROM wallets WHERE holder=?";
        Wallet wallet = null;
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeQuery();
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                wallet = Wallet.builder()
                        .id(rs.getInt("id"))
                        .userID(rs.getInt("holder"))
                        .build();
            }
        } catch (SQLException e) {
            logSQLException(e);
        }
        return wallet;
    }

    public BigDecimal findQuantityByWalletIdAndSymbol(int id, String symbol) {
        String sql = "SELECT quantity FROM symbols_in_wallet WHERE wallet_id = ? AND symbol = ?";
        BigDecimal quantity = null;
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, symbol);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                quantity = rs.getBigDecimal("quantity");
            }


        } catch (SQLException e) {
            logSQLException(e);
        }
        return quantity;
    }

    @Override
    public void updateSymbolQuantityForWallet(int walletId, String symbol, BigDecimal newQuantity) {
        String sql = """
                INSERT INTO symbols_in_wallet (wallet_id, symbol, quantity)
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE quantity = VALUES(quantity)
                """;

        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, walletId);
            stmt.setString(2, symbol);
            stmt.setBigDecimal(3, newQuantity);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logSQLException(e);
        }
    }

    @Override
    public Map<String, BigDecimal> getAllSymbolsByWalletId(int walletId) {
        String sql = "SELECT symbol, quantity FROM symbols_in_wallet WHERE wallet_id = ?";
        Map<String, BigDecimal> symbolQuantities = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, walletId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String symbol = rs.getString("symbol");
                BigDecimal quantity = rs.getBigDecimal("quantity");
                symbolQuantities.put(symbol, quantity);
            }

        } catch (SQLException e) {
            logSQLException(e);
        }

        return symbolQuantities;
    }

    @Override
    public void deleteAllSymbolsByWalletId(int walletId) {
        String sql = "DELETE FROM symbols_in_wallet WHERE wallet_id = ?";

        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, walletId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logSQLException(e);
        }
    }

}
