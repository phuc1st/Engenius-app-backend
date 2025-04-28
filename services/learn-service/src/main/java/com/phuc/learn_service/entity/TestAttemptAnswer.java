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
public class TestAttemptAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long questionId;
    int selectedIndex;
    boolean correct;

    @ManyToOne
    @JoinColumn(name="attempt_id")
    TestAttempt attempt;
}