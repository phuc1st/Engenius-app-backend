package com.phuc.learn_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserVocabularyTopicProgressDetailResponse {
    Long id;
    Long vocabularyTopicId;
    int studied;
    int memorized;
    int unmemorized;
    List<UserFlashCardProgressResponse> userFlashCardProgressResponseList;
}
