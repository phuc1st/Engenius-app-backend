package com.phuc.learn_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashCardCreationRequest {
    String image;
    String word;
    String phonetic;
    String audio;
    String answer;
    boolean memorized;
}
