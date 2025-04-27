package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.dto.response.ToeicTestSummaryResponse;
import com.phuc.learn_service.service.ToeicTestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/toeic-tests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToeicTestController {
    ToeicTestService toeicTestService;

    @PostMapping("create")
    public ApiResponse<ToeicTestDTO> create(@RequestBody @Valid ToeicTestDTO dto) {
        return ApiResponse.<ToeicTestDTO>builder()
                .result(toeicTestService.createToeicTest(dto)).build();
    }

    @GetMapping
    public ApiResponse<List<ToeicTestDTO>> getAll() {
        return ApiResponse.<List<ToeicTestDTO>>builder()
                .result(toeicTestService.findAll()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ToeicTestDTO> getOne(@PathVariable int id) {
        return ApiResponse.<ToeicTestDTO>builder()
                .result(toeicTestService.findById(id)).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ToeicTestDTO> update
            (@PathVariable int id, @RequestBody @Valid ToeicTestDTO dto) {
        return ApiResponse.<ToeicTestDTO>builder()
                .result(toeicTestService.update(id, dto)).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        toeicTestService.delete(id);
    }

    @GetMapping("/summary")
    public ApiResponse<List<ToeicTestSummaryResponse>> getAllSummaries() {
        return ApiResponse.<List<ToeicTestSummaryResponse>>builder()
                .result(toeicTestService.findAllSummary()).build();
    }
}
