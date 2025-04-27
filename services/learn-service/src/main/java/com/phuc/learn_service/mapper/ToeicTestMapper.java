package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.QuestionBlockDTO;
import com.phuc.learn_service.dto.QuestionDTO;
import com.phuc.learn_service.dto.ToeicPartDTO;
import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.dto.response.ToeicTestSummaryResponse;
import com.phuc.learn_service.entity.Question;
import com.phuc.learn_service.entity.QuestionBlock;
import com.phuc.learn_service.entity.ToeicPart;
import com.phuc.learn_service.entity.ToeicTest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToeicTestMapper {
    ToeicTestDTO toDto(ToeicTest entity);
    ToeicTest toEntity(ToeicTestDTO dto);

    ToeicPartDTO toDto(ToeicPart entity);
    ToeicPart toEntity(ToeicPartDTO dto);

    QuestionBlockDTO toDto(QuestionBlock entity);
    QuestionBlock toEntity(QuestionBlockDTO dto);

    QuestionDTO toDto(Question entity);
    Question toEntity(QuestionDTO dto);

    ToeicTestSummaryResponse toeicTestSummaryResponse(ToeicTest entity);
}

