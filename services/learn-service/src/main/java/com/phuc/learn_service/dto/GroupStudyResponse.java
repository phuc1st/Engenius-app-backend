package com.phuc.learn_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GroupStudyResponse {
    Long id;
    String avatar;
    String name;
    String description;
    String createdBy;
    LocalDateTime createdAt;
} 