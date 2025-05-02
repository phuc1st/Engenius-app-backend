package com.phuc.learn_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserVocabularyTopicProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userId;

    @ManyToOne
    @JoinColumn(name = "vocabulary_topic_id")
    VocabularyTopic vocabularyTopic;

    int studied;     // số flashcard đã học
    int memorized;   // số flashcard đã nhớ
    int unmemorized; // số chưa nhớ

    @OneToMany(mappedBy = "progress", cascade = CascadeType.ALL)
    List<UserFlashCardProgress> flashCardProgresses;
}
