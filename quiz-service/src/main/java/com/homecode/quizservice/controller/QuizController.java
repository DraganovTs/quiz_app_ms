package com.homecode.quizservice.controller;

import com.homecode.quizservice.model.Response;
import com.homecode.quizservice.model.dto.QuizDTO;
import com.homecode.quizservice.model.view.QuestionView;
import com.homecode.quizservice.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO){
        return quizService.createQuiz(quizDTO.getCategoryName()
                ,quizDTO.getNumQuestion()
                , quizDTO.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<Long>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,
                                              @RequestBody List<Response> responseList){
        return quizService.calculateResult(id,responseList);

    }
}
