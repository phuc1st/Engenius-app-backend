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
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int number;
    @Column(columnDefinition="TEXT")
    String text;
    @ElementCollection
    @CollectionTable(name="question_options", joinColumns=@JoinColumn(name="question_id"))
    @Column(name="option_text")
    List<String> options = new ArrayList<>();
    int correctIndex; // index đáp án chuẩn

    @ManyToOne
    @JoinColumn(name="block_id")
    QuestionBlock block;
}
