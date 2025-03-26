package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.IllegalMessageException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private static final int MAX_MESSAGE_LENGTH = 254;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Adds the given Message to the database. The persistence of the message will be successful 
     * if and only if the message_text is not null or blank, is under 255 characters, and 
     * posted_by refers to a real, existing user. If all these conditions are met, this method 
     * will return the new Message in the database, complete with newly-generated numerical ID.
     * 
     * @param message the Message to add to the database
     * @return the Message newly added to the database
     * @throws IllegalMessageException if the Messsage has text that is null, empty, or 255 characters or more, or if its associated account ID doesn't belong to an account in the database
     */
    public Message createMessage(Message message) throws IllegalMessageException {
        /*
         * Throw an exception if the message text is null, empty, or 255 or more characters,
         * or if the message's "posted by" ID doesn't belong to any account in the database.
         */
        if (message.getMessageText() == null) {
            throw new IllegalMessageException("Message text cannot be null.");
        } else if (message.getMessageText().equals("")) {
            throw new IllegalMessageException("Message text cannot be empty.");
        } else if (message.getMessageText().length() > MAX_MESSAGE_LENGTH) {
            throw new IllegalMessageException(
                "Message text must be " + MAX_MESSAGE_LENGTH + " characters or less."
            );
        } else if (!accountService.existsAccountWithId(message.getPostedBy())) {
            throw new IllegalMessageException(
                "Message is associated with account ID " + message.getPostedBy() +
                ", but no account with that ID exists."
            );
        }

        // Add the Message to the database.
        return messageRepository.save(message);
    }
}
