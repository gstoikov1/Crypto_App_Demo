package com.example.tradingappdemo.repository.user;

import com.example.tradingappdemo.exception.SuchEmailAlreadyExistsException;
import com.example.tradingappdemo.exception.SuchUsernameAlreadyExistsException;
import com.example.tradingappdemo.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface for accessing and manipulating User data from the database using JDBC.
 * This interface defines basic query methods such as finding by ID, username, or email,
 * and retrieving all users.
 */
public interface UserRepository {

    /**
     * Saves a User object entity
     *
     * @param user the user to be saved; must not be null
     * @return the User entity that has been saved or null if the insert was unsuccessful
     * @throws IllegalArgumentException if username is null
     * @throws SuchUsernameAlreadyExistsException the username of the user is already in the table
     * @throws SuchEmailAlreadyExistsException the email of the user is already in the table
     */
    User save(User user) throws SuchUsernameAlreadyExistsException, SuchEmailAlreadyExistsException;

    /**
     * Finds a user by their unique username.
     *
     * @param username the username to search for; must not be null or blank
     * @return an Optional containing the User if found, or empty if not
     * @throws IllegalArgumentException if username is null or blank
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their unique email.
     *
     * @param email the email to search for; must not be null or blank
     * @return an Optional containing the User if found, or empty if not
     * @throws IllegalArgumentException if email is null or blank
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username and password (typically used for login).
     *
     * @param username the username to match; must not be null or blank
     * @param password the password to match; must not be null or blank
     * @return an Optional containing the User if credentials match, or empty if not
     * @throws IllegalArgumentException if username or password is null or blank
     */
    Optional<User> findByUsernameAndPassword(String username, String password);

    /**
     * Finds a user by their unique database ID.
     *
     * @param id the user ID; must be a positive integer
     * @return an Optional containing the User if found, or empty if not
     * @throws IllegalArgumentException if id is less than or equal to 0
     */
    Optional<User> findById(int id);

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all User records in the database
     */
    List<User> findAll();

    void updateUserFundsByUserId(int userId, BigDecimal newFunds);

    void resetUserFundsToDefaultByUserId(int userId);
}


