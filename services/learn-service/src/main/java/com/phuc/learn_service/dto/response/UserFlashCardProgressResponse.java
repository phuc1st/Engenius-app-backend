package com.phuc.learn_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFlashCardProgressResponse {
    Long id;
    String image;
    String word;
    String phonetic;
    String audio;
    String answer;
    boolean memorized;
    LocalDateTime lastReviewed;
}
