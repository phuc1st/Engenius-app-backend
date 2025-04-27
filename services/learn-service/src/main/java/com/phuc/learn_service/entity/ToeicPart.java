package com.phuc.learn_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "toeic_parts")
public class ToeicPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int partNumber;

    @ManyToOne
    @JoinColumn(name="test_id")
    ToeicTest test;

    @OneToMany(mappedBy="part", cascade=CascadeType.ALL, orphanRemoval=true)
    List<QuestionBlock> blocks = new ArrayList<>();
}
