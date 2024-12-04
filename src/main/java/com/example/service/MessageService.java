package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;


@Service
@Transactional
public class MessageService {


    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message persistMessage(Message message) {
        return messageRepository.save(message);
    }
}
