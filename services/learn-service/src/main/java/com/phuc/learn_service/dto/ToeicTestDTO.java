package com.phuc.learn_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToeicTestDTO {
    String id;
    String name;
    Instant createdAt;
    List<ToeicPartDTO> parts;
}
