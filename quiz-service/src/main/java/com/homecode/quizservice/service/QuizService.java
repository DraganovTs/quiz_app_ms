package com.homecode.quizservice.service;

import com.homecode.quizservice.exception.QuizCreationException;
import com.homecode.quizservice.exception.QuizQuestionsRetrievalException;
import com.homecode.quizservice.exception.ResultCalculationException;
import com.homecode.quizservice.feign.QuizInterface;
import com.homecode.quizservice.model.Quiz;
import com.homecode.quizservice.model.Response;
import com.homecode.quizservice.model.view.QuestionView;
import com.homecode.quizservice.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizInterface quizInterface;

    public QuizService(QuizRepository quizRepository, QuizInterface quizInterface) {
        this.quizRepository = quizRepository;
        this.quizInterface = quizInterface;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            List<Long> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionsId(questions);
            quizRepository.save(quiz);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new QuizCreationException("Error creating quiz: " + e.getMessage());
        }
    }


    @Transactional
    public ResponseEntity<List<QuestionView>> getQuizQuestions(Long id) {
        try {
            Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizQuestionsRetrievalException("Quiz not found"));
            List<Long> questionsId = quiz.getQuestionsId();
            ResponseEntity<List<QuestionView>> questions = quizInterface.getQuestionsFromId(questionsId);
            return questions;
        } catch (Exception e) {
            throw new QuizQuestionsRetrievalException("Error retrieving quiz questions: " + e.getMessage());
        }
    }



    public ResponseEntity<Integer> calculateResult(Long id, List<Response> responses) {
        try {
            ResponseEntity<Integer> score = quizInterface.getScore(responses);
            return score;
        } catch (Exception e) {
            throw new ResultCalculationException("Error calculating result: " + e.getMessage());
        }
    }



}
