package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.model.ChatMessage;

import java.util.List;

@Service
public interface ChatMessageService {
     ChatMessage save(ChatMessage chatMessage);

     long countNewMessages(String senderId, String recipientId);

     List<ChatMessage> findChatMessages(String senderId, String recipientId);

     ChatMessage findById(Long id);

     void updateStatuses(String senderId, String recipientId, ChatMessage.MessageStatus status);
}
