package com.phuc.learn_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserFlashCardProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean memorized;
    LocalDateTime lastReviewed;

    @ManyToOne
    @JoinColumn(name = "flashcard_id")
    FlashCard flashCard;

    @ManyToOne
    @JoinColumn(name = "progress_id")
    UserVocabularyTopicProgress progress;
}
