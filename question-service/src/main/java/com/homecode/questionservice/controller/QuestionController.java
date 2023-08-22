package com.homecode.questionservice.controller;

import com.homecode.questionservice.model.view.QuestionView;
import com.homecode.questionservice.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {
    private final QuestionService questionService;



    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/allQuestions")
    public ResponseEntity<List<QuestionView>>getAllQuestions(){
       return  this.questionService.getAllQuestions();
     }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionView> getById(@PathVariable("id") Long id){
        return questionService.getQuestionByID(id);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuestionView>> getQuestionsByCategory(@PathVariable("category") String category){
        return  questionService.getQuestionByCategory(category);
    }


}
