package com.phuc.learn_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String image;
    String word;
    String phonetic;
    String audio;
    String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_topic_id")
    VocabularyTopic topic;
}
