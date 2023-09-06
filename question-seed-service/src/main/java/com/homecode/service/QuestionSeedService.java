package com.homecode.service;

import com.homecode.feign.QuestionSeedInterface;
import com.homecode.model.QuestionAddDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionSeedService {

    private final QuestionSeedInterface questionSeedInterface;

    @Autowired
    public QuestionSeedService(QuestionSeedInterface questionSeedInterface) {
        this.questionSeedInterface = questionSeedInterface;
    }

    public String seedQuestionsFromPdf(MultipartFile pdfFile) {
        try {
            List<String> questionTextList = parsePdfToQuestions(pdfFile);

            for (String questionText : questionTextList) {
                QuestionAddDTO questionAddDTO = createQuestionAddDTO(questionText);

                if (questionAddDTO != null) {
                    ResponseEntity<String> response = questionSeedInterface.addQuestion(questionAddDTO);

                    if (!response.getStatusCode().is2xxSuccessful()) {
                        return "Failed to create questions from PDF content.";
                    }

                }
            }

            return "PDF content processed successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to extract questions from PDF content.";
        }
    }

    private List<String> parsePdfToQuestions(MultipartFile pdfFile) throws IOException {
        List<String> questions = new ArrayList<>();
        try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String pdfText = stripper.getText(document);
            questions.addAll(extractQuestionsFromPdfText(pdfText));
        }
        return questions;
    }

    private List<String> extractQuestionsFromPdfText(String pdfText) {
        List<String> questions = new ArrayList<>();
        String[] lines = pdfText.split("\\n");

        StringBuilder currentQuestion = new StringBuilder();
        for (String line : lines) {
            line = line.trim();

            if (line.matches("\\d+\\..*")) {
                if (currentQuestion.length() > 0) {
                    questions.add(currentQuestion.toString());
                }
                currentQuestion = new StringBuilder(line);
            } else {
                currentQuestion.append(" ").append(line);
            }
        }

        if (currentQuestion.length() > 0) {
            questions.add(currentQuestion.toString());
        }

        return questions;
    }

    private QuestionAddDTO createQuestionAddDTO(String questionText) {
        QuestionAddDTO questionAddDTO = new QuestionAddDTO();

        // Split the text into two parts, assuming the first part is the numbering and the second part is the question
        String[] parts = questionText.split("\\.", 2);

        if (parts.length != 2) {
            // If there are not exactly two parts, something is wrong with the input
            return null;
        }

        String questionTitle = parts[1].trim();
        questionAddDTO.setQuestionTitle(questionTitle);

        // Set difficulty level and category
        questionAddDTO.setDifficultyLevel("easy");
        questionAddDTO.setCategory("java");

        // Extract options and correct answer
        Map<String, String> optionsAndCorrectAnswer = extractOptionsAndCorrectAnswer(questionText);
        if (optionsAndCorrectAnswer == null || optionsAndCorrectAnswer.isEmpty()) {
            return null;
        }

        // Set options and correct answer
        questionAddDTO.setOption1(optionsAndCorrectAnswer.get("Option1"));
        questionAddDTO.setOption2(optionsAndCorrectAnswer.get("Option2"));
        questionAddDTO.setOption3(optionsAndCorrectAnswer.get("Option3"));
        questionAddDTO.setOption4(optionsAndCorrectAnswer.get("Option4"));
        questionAddDTO.setRightAnswer(optionsAndCorrectAnswer.get("CorrectAnswer"));

        return questionAddDTO;
    }

    private Map<String, String> extractOptionsAndCorrectAnswer(String questionText) {
        Map<String, String> optionsAndCorrectAnswer = new HashMap<>();
        String[] lines = questionText.split("\\r?\\n");

        for (String line : lines) {
            line = line.trim();

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Check if it's an option line (e.g., 'a)', 'b)', 'c)', 'd)')
            if (line.matches("^[a-d]\\).+")) {
                String optionKey = line.substring(0, 1); // Extract 'a', 'b', 'c', or 'd'
                String optionText = line.substring(2).trim(); // Extract the option text

                optionsAndCorrectAnswer.put("Option" + optionKey, optionText);

                // Identify the correct answer based on the first option
                if (optionKey.equals("a")) {
                    optionsAndCorrectAnswer.put("CorrectAnswer", optionText);
                }
            }
        }

        // Ensure we have at least 4 options and a correct answer
        if (optionsAndCorrectAnswer.size() < 5) {
            return null;
        }

        return optionsAndCorrectAnswer;
    }

}


