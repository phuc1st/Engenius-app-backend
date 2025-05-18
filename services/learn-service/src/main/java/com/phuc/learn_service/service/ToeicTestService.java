package com.phuc.learn_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.phuc.learn_service.dto.ToeicPartDTO;
import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.dto.response.FileResponse;
import com.phuc.learn_service.dto.response.ToeicTestSummaryResponse;
import com.phuc.learn_service.entity.Question;
import com.phuc.learn_service.entity.QuestionBlock;
import com.phuc.learn_service.entity.ToeicPart;
import com.phuc.learn_service.entity.ToeicTest;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.ToeicTestMapper;
import com.phuc.learn_service.repository.ToeicTestRepository;
import com.phuc.learn_service.repository.httpclient.FileClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToeicTestService {
    ToeicTestRepository toeicTestRepository;
    ToeicTestMapper toeicTestMapper;
    FileClient fileClient;

    public ToeicTestDTO createToeicTest(ToeicTestDTO request) {
        ToeicTest toeicTest = toeicTestMapper.toEntity(request);

        if (toeicTestRepository.existsByName(toeicTest.getName())) {
            throw new AppException(ErrorCode.TOEIC_TEST_NAME_EXIST);
        }

        linkBiDirectional(toeicTest);

        return toeicTestMapper.toDto(toeicTestRepository.save(toeicTest));
    }

    public ToeicTestDTO findById(int id) {
        return toeicTestRepository.findById(id)
                .map(toeicTestMapper::toDto)
                .orElseThrow(() -> new AppException(ErrorCode.TOEIC_TEST_NOT_EXIST));
    }

    public ToeicTestDTO update(int id, ToeicTestDTO dto) {
        ToeicTest exist = toeicTestRepository.findById(id)
                .orElseThrow(() ->  new AppException(ErrorCode.TOEIC_TEST_NOT_EXIST));
        // Chỉ update các trường cần thiết
        exist.setName(dto.getName());
        // Xoá cũ thêm mới parts nếu cần, hoặc map chi tiết hơn
        exist.getParts().clear();
        exist.getParts().addAll(toeicTestMapper.toEntity(dto).getParts());

        linkBiDirectional(exist);

        ToeicTest saved = toeicTestRepository.save(exist);
        return toeicTestMapper.toDto(saved);
    }

    public void delete(int id) {
        toeicTestRepository.deleteById(id);
    }

    public List<ToeicTestDTO> findAll() {
        return toeicTestRepository.findAll().stream()
                .map(toeicTestMapper::toDto)
                .toList();
    }

    public List<ToeicTestSummaryResponse> findAllSummary() {
        return toeicTestRepository.findAll().stream()
                .map(toeicTestMapper::toeicTestSummaryResponse)
                .toList();
    }

    public ToeicTestDTO uploadToeicTest(String toeicTestJson, List<MultipartFile> files ){
        List<FileResponse> uploadedFiles = new ArrayList<>();

        try {
            // Validate input
            validateInput(toeicTestJson, files);

            // Upload files
            Map<String, String> fileUrls = uploadFiles(files, uploadedFiles);

            // Parse và update JSON
            ToeicTestDTO testDTO = parseAndUpdateJson(toeicTestJson, fileUrls);

            // Save to database
            ToeicTestDTO savedTest = createToeicTest(testDTO);

            return savedTest;

        } catch (AppException e) {
            rollbackUploadedFiles(uploadedFiles);
            throw e;
        }
    }

    private void validateInput(String toeicTestJson, List<MultipartFile> files) {
        if (toeicTestJson == null || toeicTestJson.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        if (files == null || files.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
    }

    private Map<String, String> uploadFiles(List<MultipartFile> files, List<FileResponse> uploadedFiles) {
        Map<String, String> fileUrls = new HashMap<>();
        for (MultipartFile file : files) {
            FileResponse fileResponse = fileClient.uploadMedia(file).getResult();
            uploadedFiles.add(fileResponse);
            fileUrls.put(file.getOriginalFilename(), fileResponse.getUrl());
        }
        return fileUrls;
    }

    private ToeicTestDTO parseAndUpdateJson(String toeicTestJson, Map<String, String> fileUrls) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            ToeicTestDTO testDTO = objectMapper.readValue(toeicTestJson, ToeicTestDTO.class);
            updateMediaUrls(testDTO, fileUrls);
            return testDTO;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
    }

    private void rollbackUploadedFiles(List<FileResponse> uploadedFiles) {
        for (FileResponse file : uploadedFiles) {
            try {
                fileClient.deleteFile(getFileNameFromUrl(file.getUrl()));
            } catch (Exception ex) {
                log.error("Lỗi khi xóa file {}: {}", file.getUrl(), ex.getMessage());
            }
        }
    }
    private void updateMediaUrls(ToeicTestDTO testDTO, Map<String, String> fileUrls) {
        for (ToeicPartDTO part : testDTO.getParts()) {
            for (var block : part.getBlocks()) {
                if (block.getImageUrl() != null) {
                    block.setImageUrl(fileUrls.get(block.getImageUrl()));
                }
                if (block.getAudioUrl() != null) {
                    block.setAudioUrl(fileUrls.get(block.getAudioUrl()));
                }
            }
        }
    }

    private String getFileNameFromUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.trim().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        // Loại bỏ dấu / ở cuối nếu có
        fileUrl = fileUrl.trim();
        if (fileUrl.endsWith("/")) {
            fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
        }

        int lastSlashIndex = fileUrl.lastIndexOf("/");
        if (lastSlashIndex == -1 || lastSlashIndex == fileUrl.length() - 1) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        return fileUrl.substring(lastSlashIndex + 1);
    }

    private void linkBiDirectional(ToeicTest test) {
        for (ToeicPart part : test.getParts()) {
            part.setTest(test);
            for (QuestionBlock block : part.getBlocks()) {
                block.setPart(part);
                for (Question q : block.getQuestions()) {
                    q.setBlock(block);
                }
            }
        }
    }

}