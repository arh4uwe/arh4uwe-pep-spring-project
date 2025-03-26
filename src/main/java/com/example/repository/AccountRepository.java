package com.example.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    /**
	 * Returns whether an account with the given username exists.
	 *
	 * @param username the username to search for
	 * @return {@literal true} if an account with the given username exists, {@literal false} otherwise.
	 */
    boolean existsByUsername(String username);

    /**
	 * Retrieves an account by its username and password.
	 *
	 * @param username the username to search for
     * @param password the password to search for
	 * @return the entity with the given username and password or {@literal Optional#empty()} if none found.
	 */
    Optional<Account> findByUsernameAndPassword(String username, String password);
}
