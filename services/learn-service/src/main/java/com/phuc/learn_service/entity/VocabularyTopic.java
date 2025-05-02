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
public class VocabularyTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String topicName;
    boolean isNew;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FlashCard> flashCards = new ArrayList<>();

}
