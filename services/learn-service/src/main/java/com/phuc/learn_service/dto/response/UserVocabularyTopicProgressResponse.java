package com.phuc.learn_service.dto.response;

import com.phuc.learn_service.entity.VocabularyTopic;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserVocabularyTopicProgressResponse {
    Long id;
    Long vocabularyTopicId;
    String topicName;
    int studied;
    int memorized;
    int unmemorized;
    boolean newTopic;
    int accuracy = 0;
}
