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
@Table(name = "question_blocks")
public class QuestionBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String audioUrl;
    String imageUrl;
    @Column(columnDefinition="TEXT")
    String passage;

    @ManyToOne
    @JoinColumn(name="part_id")
    ToeicPart part;

    @OneToMany(mappedBy="block", cascade=CascadeType.ALL, orphanRemoval=true)
    List<Question> questions = new ArrayList<>();
}