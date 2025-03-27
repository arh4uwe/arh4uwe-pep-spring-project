package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.IllegalMessageException;
import com.example.exception.ResourceNotFoundException;
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

    /**
     * Returns an Iterable containing every Message in the database.
     * 
     * @return an Iterable containing every Message in the database
     */
    public Iterable<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Returns the Message in the database with the given numerical ID, or {@literal null} if 
     * there is no such Message.
     * 
     * @param id a numerical ID to search for
     * @return the Message in the database with the given ID, or {@literal null} if there is no such Message
     */
    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    /**
     * Deletes the Message in the database with the given numerical ID, if such a Message exists. 
     * This method returns the Message that was deleted, or {@literal null} if there is no such 
     * Message.
     * 
     * @param id a numerical ID to search for
     * @return the Message with the given ID that was in the database, or {@literal null} if no message with that ID was in the database.
     */
    public Message deleteMessageById(int id) {
        // Get the message (or lack thereof) by ID
        Optional<Message> deletedMessage = messageRepository.findById(id);
        
        // If there is such a message, delete it and return it.
        if (deletedMessage.isPresent()) {
            messageRepository.deleteById(id);
            return deletedMessage.get();
        }

        // Otherwise, return null.
        return null;
    }

    /**
     * Updates the message with the given numerical ID by replacing its message text with the 
     * given text. The update of a message will be successful if and only if the message ID 
     * already exists and the new message_text is not null or blank and is not over 255 characters.
     * 
     * @param id a numerical ID to search for
     * @param newMessageText updated message text for the message with the given ID
     * @return the number of messages in the database that were updated. Should always be 1, assuming the method doesn't throw anything.
     * @throws IllegalMessageException if the message text is null, blank, or more than 254 characters
     * @throws ResourceNotFoundException if there is no message in the database with the given ID
     */
    public int updateMessageById(int id, String newMessageText)
            throws IllegalMessageException, ResourceNotFoundException {
        /*
         * If the new message text is null, blank, or more than 254 characters, 
         * throw an Exception.
         */
        if (newMessageText == null) {
            throw new IllegalMessageException("Message text cannot be null.");
        } else if (newMessageText.equals("")) {
            throw new IllegalMessageException("Message text cannot be empty.");
        } else if (newMessageText.length() > MAX_MESSAGE_LENGTH) {
            throw new IllegalMessageException(
                "Message text cannot be more than " + MAX_MESSAGE_LENGTH + " characters long."
            );
        }

        /*
         * Get the message in the database with the given ID, 
         * or throw an Exception if there is no such message.
         */
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("No message found with ID " + id + ".");
        });

        // Update the message text and resubmit the message into the database.
        message.setMessageText(newMessageText);
        messageRepository.save(message);

        // Return the number of rows updated. At this point, this value should always be 1.
        return 1;
    }

    /**
     * Returns an Iterable containing every Message in the database posted by the Account with the 
     * given ID, or an empty Iterable if no such Account exists.
     * 
     * @param accountId a numerical ID that may or may not be associated with an Account in the database
     * @return an Iterable containing every Message posted by the Account with the given ID
     */
    public Iterable<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
