package com.homecode.questionservice.init;

import com.homecode.questionservice.model.Question;
import com.homecode.questionservice.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeedQuestions implements CommandLineRunner {

    private final QuestionService questionService;

    public SeedQuestions(QuestionService questionService) {
        this.questionService = questionService;
    }


    @Override
    public void run(String... args)  {

        if (questionService.isEmpty()){
            questionService.saveAll(generatedQuestions());
        }


    }

    private List<Question> generatedQuestions() {

        List<Question> questions = new ArrayList<>();


        Question question1 = Question.builder()
                .difficultyLevel("easy")
                .option1("Option01")
                .option2("Option02")
                .option3("Option03")
                .option4("Option04")
                .questionTitle("TestTitle1")
                .rightAnswer("Option01")
                .category("Java")
                .build();

        questions.add(question1);

        Question question2 = Question.builder()
                .difficultyLevel("medium")
                .option1("Option01")
                .option2("Option02")
                .option3("Option03")
                .option4("Option04")
                .questionTitle("TestTitle2")
                .rightAnswer("Option02")
                .category("Java")
                .build();

        questions.add(question2);

        Question question3 = Question.builder()
                .difficultyLevel("hard")
                .option1("Option01")
                .option2("Option02")
                .option3("Option03")
                .option4("Option04")
                .questionTitle("TestTitle3")
                .rightAnswer("Option03")
                .category("Java")
                .build();

        questions.add(question3);

        return questions;
    }
}
