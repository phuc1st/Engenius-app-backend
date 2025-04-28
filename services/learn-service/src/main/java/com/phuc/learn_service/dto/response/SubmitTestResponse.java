package com.phuc.learn_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmitTestResponse {
    int correctCount;
    int totalQuestion;
    double score;
}

