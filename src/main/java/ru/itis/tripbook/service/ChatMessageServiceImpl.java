package ru.itis.tripbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tripbook.annotation.Loggable;
import ru.itis.tripbook.exception.ResourceNotFoundException;
import ru.itis.tripbook.model.ChatMessage;
import ru.itis.tripbook.repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    @Loggable
    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(ChatMessage.MessageStatus.RECEIVED);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }
    @Loggable
    @Override
    public long countNewMessages(String senderId, String recipientId) {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, ChatMessage.MessageStatus.RECEIVED);
    }
    @Loggable
    @Override
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(cId -> chatMessageRepository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, ChatMessage.MessageStatus.DELIVERED);
        }

        return messages;
    }
    @Loggable
    @Override
    public ChatMessage findById(Long id) {
        return chatMessageRepository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(ChatMessage.MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }
    @Loggable
    @Override
    public void updateStatuses(String senderId, String recipientId, ChatMessage.MessageStatus status) {
        var chatMessages =
                chatMessageRepository.findBySenderIdAndRecipientId(senderId,
                        recipientId);
        for (var chatMessage: chatMessages
             ) {
            chatMessage.setStatus(status);
            chatMessageRepository.save(chatMessage);
        }
    }
}
