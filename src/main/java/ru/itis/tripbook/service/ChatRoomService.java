package ru.itis.tripbook.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ChatRoomService {
    Optional<String> getChatId(
            String senderId, String recipientId, boolean createIfNotExist);
}
