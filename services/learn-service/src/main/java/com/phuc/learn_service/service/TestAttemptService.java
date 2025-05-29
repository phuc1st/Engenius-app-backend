package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.request.AnswerRequest;
import com.phuc.learn_service.dto.request.SubmitTestRequest;
import com.phuc.learn_service.dto.response.SubmitTestResponse;
import com.phuc.learn_service.dto.response.TestAttemptAnswerResponse;
import com.phuc.learn_service.dto.response.TestAttemptResponse;
import com.phuc.learn_service.entity.Question;
import com.phuc.learn_service.entity.TestAttempt;
import com.phuc.learn_service.entity.TestAttemptAnswer;
import com.phuc.learn_service.entity.ToeicTest;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.TestAttemptMapper;
import com.phuc.learn_service.repository.AnsweredQuestionRepository;
import com.phuc.learn_service.repository.TestAttemptRepository;
import com.phuc.learn_service.repository.ToeicTestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestAttemptService {
    ToeicTestRepository toeicTestRepository;
    TestAttemptRepository testAttemptRepository;
    AnsweredQuestionRepository answeredQuestionRepository;
    TestAttemptMapper testAttemptMapper;

    public SubmitTestResponse submitTest(SubmitTestRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        ToeicTest toeicTest = toeicTestRepository.findById(request.getTestId())
                .orElseThrow(() -> new AppException(ErrorCode.TOEIC_TEST_NOT_EXIST));
        Optional<TestAttempt> existingAttemptOpt =
                testAttemptRepository.findByUserIdAndToeicTest_Id
                        (userId, request.getTestId());

        TestAttempt testAttempt = existingAttemptOpt.orElseGet(() -> {
            TestAttempt newAttempt = testAttemptMapper.toTestAttempt(request);
            newAttempt.setUserId(userId);
            newAttempt.setToeicTest(toeicTest);
            return newAttempt;
        });
        Map<Long, Question> questionMap = toeicTest.getParts().stream()
                .flatMap(part -> part.getBlocks().stream())
                .flatMap(questionBlock -> questionBlock.getQuestions().stream())
                .collect(Collectors.toMap(Question::getId, q -> q));

        int correctCount = 0;

        List<TestAttemptAnswer> newAnswers = new ArrayList<>();
        for (AnswerRequest answerRequest : request.getAnswers()) {
            Question question = questionMap.get(answerRequest.getQuestionId());
             if (question == null) continue;

            boolean correct = Objects.equals(answerRequest.getSelectedIndex(), question.getCorrectIndex());
            if (correct) correctCount++;

            newAnswers.add(TestAttemptAnswer.builder()
                    .question(question)
                    .selectedIndex(answerRequest.getSelectedIndex())
                    .correct(correct)
                    .attempt(testAttempt)
                    .build());
        }

        int totalQuestion = questionMap.size();
        double score = (totalQuestion == 0) ? 0 : (((double) correctCount / totalQuestion) * 100);

        if (testAttempt.getAnswers() != null) {
            testAttempt.getAnswers().clear();
            testAttempt.getAnswers().addAll(newAnswers); // thêm mới
        } else {
            testAttempt.setAnswers(newAnswers); // chỉ dành cho attempt mới
        }
        testAttempt.setCorrectCount(correctCount);
        testAttempt.setTotalQuestion(totalQuestion);
        testAttempt.setScore(score);
        testAttemptRepository.save(testAttempt);

        return SubmitTestResponse.builder()
                .correctCount(correctCount)
                .totalQuestion(totalQuestion)
                .score(score)
                .build();
    }

    public List<TestAttemptAnswerResponse> getTestAttempt(int testId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Optional<TestAttempt> existingAttemptOpt =
                testAttemptRepository.findByUserIdAndToeicTest_Id
                        (userId, testId);

        return existingAttemptOpt.map(testAttempt -> testAttempt.getAnswers().stream()
                .map(testAttemptMapper::testAttemptAnswerResponse).toList()).orElse(Collections.emptyList());
    }

    public void saveTestAttempt(SubmitTestRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        ToeicTest toeicTest = toeicTestRepository.findById(request.getTestId())
                .orElseThrow(() -> new AppException(ErrorCode.TOEIC_TEST_NOT_EXIST));
        Optional<TestAttempt> existingAttemptOpt =
                testAttemptRepository.findByUserIdAndToeicTest_Id
                        (userId, request.getTestId());

        TestAttempt testAttempt = existingAttemptOpt.orElseGet(() -> {
            TestAttempt newAttempt = testAttemptMapper.toTestAttempt(request);
            newAttempt.setUserId(userId);
            newAttempt.setToeicTest(toeicTest);
            return newAttempt;
        });
        Map<Long, Question> questionMap = toeicTest.getParts().stream()
                .flatMap(part -> part.getBlocks().stream())
                .flatMap(questionBlock -> questionBlock.getQuestions().stream())
                .collect(Collectors.toMap(Question::getId, q -> q));

        List<TestAttemptAnswer> newAnswers = new ArrayList<>();
        for (AnswerRequest answerRequest : request.getAnswers()) {
            Question question = questionMap.get(answerRequest.getQuestionId());
            if (question == null) continue;

            newAnswers.add(TestAttemptAnswer.builder()
                    .question(question)
                    .selectedIndex(answerRequest.getSelectedIndex())
                    .attempt(testAttempt)
                    .build());
        }

        if (testAttempt.getAnswers() != null) {
            testAttempt.getAnswers().clear();
            testAttempt.getAnswers().addAll(newAnswers); // thêm mới
        } else {
            testAttempt.setAnswers(newAnswers); // chỉ dành cho attempt mới
        }
        testAttemptRepository.save(testAttempt);
    }
}
