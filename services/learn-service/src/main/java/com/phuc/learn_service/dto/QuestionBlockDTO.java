package com.phuc.learn_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionBlockDTO {
    Long id;
    String audioUrl;
    String imageUrl;
    String passage;
    List<QuestionDTO> questions;
}
