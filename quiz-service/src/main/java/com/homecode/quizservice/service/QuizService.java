package com.homecode.quizservice.service;

import com.homecode.quizservice.feign.QuizInterface;
import com.homecode.quizservice.model.Quiz;
import com.homecode.quizservice.model.Response;
import com.homecode.quizservice.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<Long>> getQuizQuestions(Integer id) {

//        List<Question> questionsFromDB = getQuestions(id);
//        List<QuestionToClientDTO> questionsDTOList = questionsFromDB
//                .stream()
//                .map(this::mapQuestionToDTO)
//                .toList();

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responseList) {
//        List<Question> questionsFromDB = getQuestions(id);
//        Integer correctAnswers = checkClientResult(questionsFromDB, responseList);
//

        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    private Integer checkClientResult(List<Long> questionsFromDB, List<Response> responseList) {
//        Integer correctAnswer = 0;
//        for (int i = 0; i < questionsFromDB.size(); i++) {
//            if (questionsFromDB.get(i).getRightAnswer().equals(responseList.get(i).getAnswer())) {
//                correctAnswer++;
//            }
//        }

        return null;
    }

    private List<Long> getQuestions(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        return null;
    }


}
