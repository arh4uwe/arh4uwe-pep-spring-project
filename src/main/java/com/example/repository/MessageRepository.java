package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    /**
	 * Returns whether a message posted by the user with the given account ID exists.
	 *
	 * @param username the account ID to search for
	 * @return {@literal true} if a message posted by the user with the given account ID exists, {@literal false} otherwise.
	 */
    Iterable<Message> findByPostedBy(Integer postedBy);
}
