package ru.itis.tripbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatMessage {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Timestamp timestamp;
    private MessageStatus status;

    public enum MessageStatus {
        RECEIVED, DELIVERED
    }
}
