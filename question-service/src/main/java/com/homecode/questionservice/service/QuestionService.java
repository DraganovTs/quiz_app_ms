package com.homecode.questionservice.service;

import com.homecode.questionservice.exception.NoDataFoundException;
import com.homecode.questionservice.model.Question;
import com.homecode.questionservice.model.view.QuestionView;
import com.homecode.questionservice.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    //Init methods
    public boolean isEmpty() {
        return this.questionRepository.count() == 0;
    }

    public void saveAll(List<Question> generatedQuestions) {
        this.questionRepository.saveAll(generatedQuestions);
    }

    //Production methods
    public ResponseEntity<List<QuestionView>> getAllQuestions() {
        List<QuestionView> questions = questionRepository.findAll()
                .stream()
                .map(this::mapQuestionToView)
                .collect(Collectors.toList());

        if (questions.isEmpty()) {
            throw new NoDataFoundException("No questions available.");
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<QuestionView> getQuestionByID(Long id) {
        if (questionRepository.findById(id).isPresent()) {
            QuestionView questionView = mapQuestionToView(questionRepository.findById(id).get());
            return new ResponseEntity<>(questionView, HttpStatus.OK);
        } else {
            throw new NoDataFoundException("No question with this ID: " + id);
        }
    }

    public ResponseEntity<List<QuestionView>> getQuestionByCategory(String category) {

        List<QuestionView> questionsByCategory = questionRepository.findAllByCategory(category)
                .stream()
                .map(this::mapQuestionToView)
                .collect(Collectors.toList());

        if (questionsByCategory.isEmpty()) {
            throw new NoDataFoundException("No questions in this category.");
        }

        return new ResponseEntity<>(questionsByCategory, HttpStatus.OK);

    }


    private QuestionView mapQuestionToView(Question q) {
        return QuestionView.builder()
                .id(q.getId())
                .questionTitle(q.getQuestionTitle())
                .category(q.getCategory())
                .difficultyLevel(q.getDifficultyLevel())
                .rightAnswer(q.getRightAnswer())
                .option1(q.getOption1())
                .option2(q.getOption2())
                .option3(q.getOption3())
                .option4(q.getOption4())
                .build();

    }


}
