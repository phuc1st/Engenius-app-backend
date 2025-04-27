package com.phuc.learn_service.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ToeicTestSummaryResponse {
    private int id;
    private String name;
    private Instant createdAt;
}
