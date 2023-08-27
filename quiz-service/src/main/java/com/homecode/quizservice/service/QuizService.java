package com.homecode.quizservice.service;

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

        List<Long> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsId(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<List<QuestionView>> getQuizQuestions(Long id) {

        Quiz quiz = quizRepository.findById(id).get();
        List<Long> questionsId = quiz.getQuestionsId();
        ResponseEntity<List<QuestionView>> questions = quizInterface.getQuestionsFromId(questionsId);
        return questions;
    }


    public ResponseEntity<Integer> calculateResult(Long id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }


}
