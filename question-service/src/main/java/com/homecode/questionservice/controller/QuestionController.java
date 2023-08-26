package com.homecode.questionservice.controller;

import com.homecode.questionservice.model.Response;
import com.homecode.questionservice.model.dto.QuestionAddDTO;
import com.homecode.questionservice.model.view.QuestionView;
import com.homecode.questionservice.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionAddDTO questionAddDTO){
        return  questionService.addQuestion(questionAddDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestionByID(@PathVariable("id") Long id
            ,@Valid @RequestBody QuestionAddDTO questionAddDTO){
        return questionService.updateQuestionByID(id,questionAddDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") Long id){
        return questionService.deleteQuestion(id);
    }
//Quiz-service
    @GetMapping("generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz(
            @RequestParam String categoryName,
            @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionView>> getQuestionsFromId(@RequestBody List<Long> questionsId){
        return questionService.getQuestionsById(questionsId);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }


}
