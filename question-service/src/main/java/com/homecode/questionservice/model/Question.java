package com.homecode.questionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question_title",nullable = false,unique = true)
    private String questionTitle;
    @Column(name = "option_1",nullable = false)
    private String option1;
    @Column(name = "option_2",nullable = false)
    private String option2;
    @Column(name = "option_3",nullable = false)
    private String option3;
    @Column(name = "option_4",nullable = false)
    private String option4;
    @Column(name = "right_answer",nullable = false)
    private String rightAnswer;
    @Column(name = "difficulty_Level",nullable = false)
    private String difficultyLevel;
    @Column(nullable = false)
    private String category;
}
