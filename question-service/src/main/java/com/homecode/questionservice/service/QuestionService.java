package com.homecode.questionservice.service;

import com.homecode.questionservice.exception.*;
import com.homecode.questionservice.model.Question;
import com.homecode.questionservice.model.Response;
import com.homecode.questionservice.model.dto.QuestionAddDTO;
import com.homecode.questionservice.model.view.QuestionView;
import com.homecode.questionservice.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ResponseEntity<String> addQuestion(QuestionAddDTO questionAddDTO) {

        Question questionToAdd = mapQuestionToAddToQuestion(questionAddDTO);
        try {
            questionRepository.save(questionToAdd);
            return new ResponseEntity<>("Question is added", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new QuestionNotAddedException("Question could not be added: " + e.getMessage());
        }

    }

    public ResponseEntity<String> updateQuestionByID(Long id, QuestionAddDTO questionAddDTO) {

        try {
            Question qn = questionRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Question with ID: " + id + " Not Found!"));

            Question questionToSave = mapQuestionToAddToQuestion(questionAddDTO);
            questionToSave.setId(qn.getId());
            questionRepository.save(questionToSave);
            return ResponseEntity.ok().body("Question is updated");
        } catch (Exception e) {
            throw new QuestionUpdateException("Question could not be updated: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteQuestion(Long id) {
        try {
            Question question = questionRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Question with ID: " + id + " Not Found!"));

            questionRepository.deleteById(question.getId());

            return ResponseEntity.ok().body("Question deleted with success!");
        } catch (Exception e) {
            throw new QuestionDeleteException("Question could not be deleted: " + e.getMessage());
        }
    }
// Quiz-service
    public ResponseEntity<List<Long>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        try {
            List<Long> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQuestions);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            throw new QuizQuestionsRetrieveException("Error retrieving questions for quiz: " + e.getMessage());
        }
    }

    public ResponseEntity<List<QuestionView>> getQuestionsById(List<Long> questionsId) {

        List<Question> questions = new ArrayList<>();
        for(Long id : questionsId){
           questions.add( questionRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Question with ID: " + id + " Not Found!")));
        }
        List<QuestionView> questionsView = questions
                .stream()
                .map(this::mapQuestionToView)
                .collect(Collectors.toList());

        return new ResponseEntity<>(questionsView,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;

        for (Response response: responses){
            Question question = questionRepository.findById(response.getId())
                    .orElseThrow(() -> new NoDataFoundException("Question with ID: " + response.getId() + " Not Found!"));

            if (question.getRightAnswer().equals(response.getAnswer())){
                right++;
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
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

    private Question mapQuestionToAddToQuestion(QuestionAddDTO q) {
        return Question.builder()
                .questionTitle(q.getQuestionTitle())
                .rightAnswer(q.getRightAnswer())
                .difficultyLevel(q.getDifficultyLevel())
                .category(q.getCategory())
                .option1(q.getOption1())
                .option2(q.getOption2())
                .option3(q.getOption3())
                .option4(q.getOption4())
                .build();
    }



}
