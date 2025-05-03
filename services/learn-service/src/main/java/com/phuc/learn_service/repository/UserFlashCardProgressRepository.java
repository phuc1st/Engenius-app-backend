package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.FlashCard;
import com.phuc.learn_service.entity.UserFlashCardProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFlashCardProgressRepository extends JpaRepository<UserFlashCardProgress, Long> {
    Optional<UserFlashCardProgress> findByProgressIdAndFlashCardId(Long progressId, Long flashCardId);
    List<UserFlashCardProgress> findByProgressId(Long progressId);
}