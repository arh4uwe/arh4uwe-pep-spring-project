package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.IllegalCredentialsException;
import com.example.exception.IllegalMessageException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UnauthorizedCredentialsException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("register")
    public Account register(@RequestBody Account account) {
        return accountService.register(account);
    }

    @PostMapping("login")
    public Account login(@RequestBody Account account) {
        return accountService.login(account);
    }

    @PostMapping("messages")
    public Message createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("messages")
    public Iterable<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{messageId}")
    public Message getMessageById(@PathVariable int messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        int messagesDeleted = messageService.deleteMessageById(messageId);

        // Response body will be empty if no messages were deleted
        if (messagesDeleted == 0) {
            return ResponseEntity.ok().build();
        }
        // Otherwise, response body will contain the number of rows that were deleted
        else {
            return ResponseEntity.ok().body(messagesDeleted);
        }
    }

    @PatchMapping("messages/{messageId}")
    public int updateMessageById(@PathVariable int messageId, @RequestBody Message message) {
        return messageService.updateMessageById(messageId, message.getMessageText());
    }

    @GetMapping("accounts/{accountId}/messages")
    public Iterable<Message> getMessagesByAccountId(@PathVariable int accountId) {
        return messageService.getAllMessagesByAccountId(accountId);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalCredentials(IllegalCredentialsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalMessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalCredentials(IllegalMessageException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleResourceNotFound(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UnauthorizedCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedCredentials(UnauthorizedCredentialsException ex) {
        return ex.getMessage();
    }
}
