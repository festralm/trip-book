package ru.itis.tripbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tripbook.model.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
