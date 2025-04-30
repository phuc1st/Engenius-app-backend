package com.phuc.learn_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestAttemptAnswerResponse {
    Long id;
    Long questionId;
    int number;
    int selectedIndex;
    boolean correct;
}
