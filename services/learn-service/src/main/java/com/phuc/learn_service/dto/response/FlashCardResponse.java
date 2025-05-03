package com.phuc.learn_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashCardResponse {
    Long id;
    String image;
    String word;
    String phonetic;
    String audio;
    String answer;
}
