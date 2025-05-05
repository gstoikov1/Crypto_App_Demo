package com.example.tradingappdemo.logging;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.LocalDateTime;

public final class ExceptionLogger {
    public static final String EXCEPTION_LOGGING_FILE = "logging.txt";

    public static void logSQLException(SQLException e) {

        try (PrintStream ps = new PrintStream(new FileOutputStream(EXCEPTION_LOGGING_FILE, true))) {
            System.out.println("Exception encountered while executing sql query - error can be found in " + EXCEPTION_LOGGING_FILE);
            ps.println("Exception encountered at " + LocalDateTime.now());
            e.printStackTrace(ps);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not open file. Printing in console");
            ex.printStackTrace();
        }
    }


    private ExceptionLogger() {

    }
}
