package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.service.ToeicTestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/toeic-tests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToeicTestController {
    ToeicTestService toeicTestService;

    @PostMapping("create")
    public ToeicTestDTO create(@RequestBody @Valid ToeicTestDTO dto) {
        return toeicTestService.createToeicTest(dto);
    }
}
