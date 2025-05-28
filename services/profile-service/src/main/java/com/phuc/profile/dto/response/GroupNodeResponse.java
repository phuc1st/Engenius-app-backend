package com.phuc.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupNodeResponse {
    private String id;
    private String name;
    private String createdBy;
    private String avatarUrl;
    private int memberCount;
    private boolean joined;
}
