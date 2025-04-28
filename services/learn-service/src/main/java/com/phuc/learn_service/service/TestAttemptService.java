package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.request.SubmitTestRequest;
import com.phuc.learn_service.dto.response.TestAttemptResponse;
import com.phuc.learn_service.entity.*;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.TestAttemptMapper;
import com.phuc.learn_service.repository.AnsweredQuestionRepository;
import com.phuc.learn_service.repository.TestAttemptRepository;
import com.phuc.learn_service.repository.ToeicTestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestAttemptService {
    ToeicTestRepository toeicTestRepository;
    TestAttemptRepository testAttemptRepository;
    AnsweredQuestionRepository answeredQuestionRepository;
    TestAttemptMapper testAttemptMapper;

    public TestAttemptResponse submitTest(SubmitTestRequest request) {
        ToeicTest toeicTest = toeicTestRepository.findById(request.getTestId())
                .orElseThrow(() -> new AppException(ErrorCode.TOEIC_TEST_NOT_EXIST));
        Optional<TestAttempt> existingAttemptOpt =
                testAttemptRepository.findByUserIdAndToeicTest_Id
                        (request.getUserId(), request.getTestId());

        TestAttempt testAttempt = existingAttemptOpt.orElseGet(() -> {
            TestAttempt newAttempt = testAttemptMapper.toTestAttempt(request);
            newAttempt.setToeicTest(toeicTest);
            return newAttempt;
        });

        Map<Long, Integer> correctAnswerMap = new HashMap<>();

        for (ToeicPart part : toeicTest.getParts()) {
            for (QuestionBlock questionBlock : part.getBlocks()) {
                for (Question question : questionBlock.getQuestions()) {
                    correctAnswerMap.put(question.getId(), question.getCorrectIndex());
                }
            }
        }

        int correctCount = 0;

        for (TestAttemptAnswer testAttemptAnswer : testAttempt.getAnswers()) {
            Integer correctIndex = correctAnswerMap.get(testAttemptAnswer.getQuestionId());
            boolean correct = Objects.equals(testAttemptAnswer.getSelectedIndex(), correctIndex);

            if (correct) correctCount++;

            testAttemptAnswer.setCorrect(correct);
            testAttemptAnswer.setAttempt(testAttempt);
        }

        int totalQuestion = correctAnswerMap.size();
        double score = (totalQuestion == 0) ? 0 : (((double) correctCount / totalQuestion) * 100);

        testAttempt.setCorrectCount(correctCount);
        testAttempt.setTotalQuestion(totalQuestion);
        testAttempt.setScore(score);

        return testAttemptMapper.toTestAttemptResponse
                (testAttemptRepository.save(testAttempt));
    }
}
