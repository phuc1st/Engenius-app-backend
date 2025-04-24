package com.phuc.learn_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDTO {
    Long id;
    int number;
    String text;
    List<String> options;
    int correctIndex;
}
