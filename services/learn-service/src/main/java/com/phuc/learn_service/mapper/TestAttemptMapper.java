package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.request.AnswerRequest;
import com.phuc.learn_service.dto.request.SubmitTestRequest;
import com.phuc.learn_service.dto.response.SubmitTestResponse;
import com.phuc.learn_service.dto.response.TestAttemptResponse;
import com.phuc.learn_service.entity.TestAttempt;
import com.phuc.learn_service.entity.TestAttemptAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestAttemptMapper {
    TestAttempt toTestAttempt(SubmitTestRequest request);
    SubmitTestResponse toSubmitTestResponse(TestAttempt testAttempt);

    @Mapping(source = "toeicTest.id", target = "testId")
    TestAttemptResponse toTestAttemptResponse(TestAttempt attempt);

    TestAttemptAnswer toTestAttemptAnswer(AnswerRequest answerRequest);
}
