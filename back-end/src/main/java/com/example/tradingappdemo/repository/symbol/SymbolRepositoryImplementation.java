package com.example.tradingappdemo.repository.symbol;

import com.example.tradingappdemo.model.Symbol;
import com.example.tradingappdemo.repository.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.tradingappdemo.logging.ExceptionLogger.EXCEPTION_LOGGING_FILE;
import static com.example.tradingappdemo.logging.ExceptionLogger.logSQLException;

@Repository
public class SymbolRepositoryImplementation implements SymbolRepository {

    private final DbConfig dbConfig;

    @Autowired
    public SymbolRepositoryImplementation(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public Optional<Symbol> findBySymbol(String symbol) {
        return Optional.empty();
    }

    @Override
    public List<Symbol> findAllSymbols() {
        String sql = "SELECT symbol, name, icon_url FROM symbols ";
        List<Symbol> symbols = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Symbol retrievedSymbol = Symbol.builder()
                        .name(rs.getString("name"))
                        .symbol(rs.getString("symbol"))
                        .iconURL(rs.getString("icon_url"))
                        .build();
                symbols.add(retrievedSymbol);
            }
        } catch (SQLException e) {
            logSQLException(e);
        }
        return symbols;
    }


    private Symbol getSymbol(String symbol) {
        String sql = "SELECT FROM symbols (symbol, name, icon_url) WHERE symbols.symbol = ?)";
        Symbol retrievedSymbol = null;
        try (Connection conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPass());
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, symbol);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                retrievedSymbol = Symbol.builder()
                        .name(rs.getString("name"))
                        .symbol(rs.getString("symbol"))
                        .iconURL(rs.getString("icon_url"))
                        .build();
            }
        } catch (SQLException e) {
            logSQLException(e);
        }
        return retrievedSymbol;
    }

}
