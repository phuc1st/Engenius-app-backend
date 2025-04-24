package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
