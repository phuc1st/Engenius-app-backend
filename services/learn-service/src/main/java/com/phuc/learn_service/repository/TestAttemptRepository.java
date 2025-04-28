package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestAttemptRepository extends JpaRepository<TestAttempt, String> {
    Optional<TestAttempt> findByUserIdAndToeicTest_Id(String userId, int testId);
}
