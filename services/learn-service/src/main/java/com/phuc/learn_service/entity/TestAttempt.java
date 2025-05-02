package com.phuc.learn_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "test_attempt", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "test_id"})
})
@Entity
public class TestAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    ToeicTest toeicTest;

    Instant startedAt;
    Instant submittedAt;
    int correctCount;
    int totalQuestion;
    double score;

    @OneToMany(mappedBy="attempt", cascade=CascadeType.ALL, orphanRemoval = true)
    List<TestAttemptAnswer> answers;
}


