package com.phuc.learn_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToeicPartDTO {
    Long id;
    int partNumber;
    List<QuestionBlockDTO> blocks;
}
