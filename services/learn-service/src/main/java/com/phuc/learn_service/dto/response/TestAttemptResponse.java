package com.phuc.learn_service.dto.response;

import com.phuc.learn_service.entity.TestAttemptAnswer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestAttemptResponse {
    String id;
    String userId;
    int testId;
    Instant startedAt;
    Instant submittedAt;
    int correctCount;
    int totalQuestion;
    double score;
    List<TestAttemptAnswerResponse> answers;
}
