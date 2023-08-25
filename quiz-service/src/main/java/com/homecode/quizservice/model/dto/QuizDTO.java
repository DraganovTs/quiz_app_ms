package com.homecode.quizservice.model.dto;

import lombok.Data;

@Data
public class QuizDTO {
    String categoryName;
    Integer numQuestion;
    String title;
}
