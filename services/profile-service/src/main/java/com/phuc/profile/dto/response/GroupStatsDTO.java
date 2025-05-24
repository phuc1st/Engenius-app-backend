package com.phuc.profile.dto.response;

import com.phuc.profile.entity.GroupNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupStatsDTO {
    private GroupNode g;
    private Long memberCount;
    private Boolean joined;
}
