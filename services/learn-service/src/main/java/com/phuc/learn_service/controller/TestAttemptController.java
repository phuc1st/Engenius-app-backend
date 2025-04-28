package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.request.SubmitTestRequest;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.dto.response.SubmitTestResponse;
import com.phuc.learn_service.dto.response.TestAttemptResponse;
import com.phuc.learn_service.entity.TestAttempt;
import com.phuc.learn_service.service.TestAttemptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-attempt")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestAttemptController {
    TestAttemptService testAttemptService;

    @PostMapping("/submit")
    public ApiResponse<TestAttemptResponse> submitTest(@RequestBody SubmitTestRequest request) {
       return  ApiResponse.<TestAttemptResponse>builder()
               .result(testAttemptService.submitTest(request))
               .build();
    }
}
