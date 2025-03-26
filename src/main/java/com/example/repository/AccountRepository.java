package com.example.repository;

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
	 * Returns whether an account with the given username and password exists.
	 *
	 * @param username the username to search for
     * @param password the password to search for
	 * @return {@literal true} if an account with the given username and password exists, {@literal false} otherwise.
	 */
    boolean existsByUsernameAndPassword(String username, String password);
}
