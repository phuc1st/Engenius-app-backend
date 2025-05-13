package com.phuc.learn_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class DailyTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    @Column(name = "experience_points", nullable = false)
    Integer experiencePoints;

    @Column(name = "is_completed", nullable = false)
    Boolean isCompleted = false;

    @Column(name = "task_type", nullable = false)
    @Enumerated(EnumType.STRING)
    TaskType taskType;

    String userId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "completed_at")
    LocalDateTime completedAt;

    public enum TaskType {
        VOCABULARY,
        GRAMMAR,
        LISTENING,
        READING,
        SPEAKING
    }
} 