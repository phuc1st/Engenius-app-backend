package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.ToeicPartDTO;
import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.dto.response.ToeicTestSummaryResponse;
import com.phuc.learn_service.entity.Question;
import com.phuc.learn_service.entity.QuestionBlock;
import com.phuc.learn_service.entity.ToeicPart;
import com.phuc.learn_service.entity.ToeicTest;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.ToeicTestMapper;
import com.phuc.learn_service.repository.ToeicTestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToeicTestService {
    ToeicTestRepository toeicTestRepository;
    ToeicTestMapper toeicTestMapper;

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
//TODO xu ly testAttempt