package com.homecode.quizservice.feign;

import com.homecode.quizservice.model.Response;
import com.homecode.quizservice.model.view.QuestionView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz(
            @RequestParam String categoryName,
            @RequestParam Integer numQuestions);


    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionView>> getQuestionsFromId(@RequestBody List<Long> questionsId);


    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}