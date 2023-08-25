package com.homecode.quizservice.service;

import com.homecode.quizservice.model.Quiz;
import com.homecode.quizservice.model.Response;
import com.homecode.quizservice.model.view.QuestionView;
import com.homecode.quizservice.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

//        List<Long> questions = questionService.findRandomQuestionsByCategory(category, numQ);
//
//        Quiz quiz = Quiz.builder()
//                .title(title)
//                .questions(questions)
//                .build();
//
//        this.quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionView>> getQuizQuestions(Integer id) {

//        List<Question> questionsFromDB = getQuestions(id);
//        List<QuestionToClientDTO> questionsDTOList = questionsFromDB
//                .stream()
//                .map(this::mapQuestionToDTO)
//                .toList();

        return new ResponseEntity<>(questionsDTOList, HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responseList) {
//        List<Question> questionsFromDB = getQuestions(id);
//        Integer correctAnswers = checkClientResult(questionsFromDB, responseList);
//

        return new ResponseEntity<>(correctAnswers,HttpStatus.OK);
    }

    private Integer checkClientResult(List<Question> questionsFromDB, List<Response> responseList) {
        Integer correctAnswer = 0;
        for (int i = 0; i < questionsFromDB.size(); i++) {
            if (questionsFromDB.get(i).getRightAnswer().equals(responseList.get(i).getAnswer())) {
                correctAnswer++;
            }
        }

        return correctAnswer;
    }

    private List<Question> getQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return quiz.get().getQuestions();
    }


}
