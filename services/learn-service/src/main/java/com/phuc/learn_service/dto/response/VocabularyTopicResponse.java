package com.phuc.learn_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VocabularyTopicResponse {
    Long id;
    String topicName;
    boolean isNew;
    int accuracy;
    int memorized;
    int unmemorized;
    int notStudied;
}
