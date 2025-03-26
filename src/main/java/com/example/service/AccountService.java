package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.IllegalCredentialsException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private static final int MIN_PASSWORD_LENGTH = 4;

    @Autowired
    AccountRepository accountRepository;

    /**
     * Adds the given Account to the database. The registration will be successful if and only 
     * if the username is non-null and not blank, the password is non-null and at least 4 
     * characters long, and an Account with that username does not already exist. If all these 
     * conditions are met, this method will return the new Account in the database, complete with 
     * newly-generated numerical ID.
     * 
     * @param account the Account to add to the database
     * @return the Account newly added to the database
     * @throws IllegalCredentialsException if the Account's username is null or empty, or if the Account's password is null or less than 4 characters
     * @throws DuplicateUsernameException if there is already an Account in the database with the given Account's username.
     */
    public Account register(Account account) 
            throws IllegalCredentialsException, DuplicateUsernameException {
        /*
         * If the account has an empty or null username, 
         * or if the password is null or has less than 4 characters, 
         * or if the username is already in use,
         * throw an exception.
         */
        if (account.getUsername() == null) {
            throw new IllegalCredentialsException("Username cannot be null.");
        } else if (account.getUsername().equals("")) {
            throw new IllegalCredentialsException("Username cannot be empty.");
        } else if (account.getPassword() == null) {
            throw new IllegalCredentialsException("Password cannot be null.");
        } else if (account.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalCredentialsException(
                "Password must contain at least " + MIN_PASSWORD_LENGTH + " characters."
            );
        } else if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateUsernameException(
                "Username " + account.getUsername() + " is already in use."
            );
        }

        // Add the account to the database.
        return accountRepository.save(account);
    }
}
