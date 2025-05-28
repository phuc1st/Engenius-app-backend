package com.phuc.profile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileGroupCreateRequest {
    String groupId;
    String avatarUrl;
    String groupName;
    String createdBy;
}
