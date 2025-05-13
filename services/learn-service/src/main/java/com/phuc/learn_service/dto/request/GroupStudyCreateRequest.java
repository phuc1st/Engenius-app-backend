package com.phuc.learn_service.dto.request;

import lombok.Data;

@Data
public class GroupStudyCreateRequest {
    String name;
    String description;
    String createdBy;
} 