package com.phuc.learn_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileGroupCreateRequest {
    String groupId;
    String groupName;
    String createdBy;
} 