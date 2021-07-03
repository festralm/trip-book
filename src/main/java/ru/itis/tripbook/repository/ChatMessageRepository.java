package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.tripbook.model.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, ChatMessage.MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    @Query("select chatMessage from ChatMessage chatMessage " +
            "where chatMessage.senderId = :sender_id and " +
            "chatMessage.recipientId = :recipient_id")
    List<ChatMessage> findBySenderIdAndRecipientId(
            @Param("sender_id") String senderId,
            @Param("recipient_id") String recipientId);
}
