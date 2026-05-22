package com.prancibot.chatserver.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Conversation {
    @Id
    @GeneratedValue
    @Column(name = "conversation_id")
    private UUID id;

    @Column(length = 100)
    private String name;

    @Column(nullable = false, updatable = false)
    private Instant creationDate;

    @OneToMany(
            mappedBy = "conversation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChatMessage> messages = new ArrayList<>();

    @PrePersist
    void onCreate() {
        this.creationDate = Instant.now();
    }
}
