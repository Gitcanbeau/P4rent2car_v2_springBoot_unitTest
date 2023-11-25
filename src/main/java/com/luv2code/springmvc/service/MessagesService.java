package com.luv2code.springmvc.service;

import com.luv2code.springmvc.dao.MessageRepository;
import com.luv2code.springmvc.entity.Message;
import com.luv2code.springmvc.requestmodels.AdminQuestionRequest;
import com.luv2code.springmvc.requestmodels.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessagesService {

    private MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean isMessagePresentCheck(String userEmail){
        Pageable pageable=PageRequest.of(0,20);

        Page<Message> messages=messageRepository.findByUserEmail(userEmail,pageable);
        if(messages.isEmpty()){
            return false;
        }
        return true;
    }

    public void postMessage(MessageRequest messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getModel(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String adminEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        if (!message.isPresent()) {
            throw new Exception("Message not found");
        }

        message.get().setAdminEmail(adminEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }

}
