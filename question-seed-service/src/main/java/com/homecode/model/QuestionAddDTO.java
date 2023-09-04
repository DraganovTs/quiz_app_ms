package com.homecode.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAddDTO {
    @NotEmpty
    private String questionTitle;
    @NotEmpty
    private String option1;
    @NotEmpty
    private String option2;
    @NotEmpty
    private String option3;
    @NotEmpty
    private String option4;
    @NotEmpty
    private String rightAnswer;
    @NotEmpty
    private String difficultyLevel;
    @NotEmpty
    private String category;
}
