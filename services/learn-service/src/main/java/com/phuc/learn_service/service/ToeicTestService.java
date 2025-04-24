package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.entity.ToeicTest;
import com.phuc.learn_service.mapper.ToeicTestMapper;
import com.phuc.learn_service.repository.ToeicTestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToeicTestService {
    ToeicTestRepository toeicTestRepository;
    ToeicTestMapper toeicTestMapper;

    public ToeicTestDTO createToeicTest(ToeicTestDTO request) {
        ToeicTest toeicTest = toeicTestMapper.toEntity(request);

        toeicTest.getParts().forEach(part -> {
            part.setTest(toeicTest);
            // và cho mỗi block:
            part.getBlocks().forEach(block -> {
                block.setPart(part);
                block.getQuestions().forEach(q -> q.setBlock(block));
            });
        });
        ToeicTestDTO response = toeicTestMapper.toDto(toeicTestRepository.save(toeicTest));
        return response;
    }
}
