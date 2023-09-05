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
import java.util.List;

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

                ResponseEntity<String> response = questionSeedInterface.addQuestion(questionAddDTO);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    return "Failed to create questions from PDF content.";
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

        // Options and correct answer handling
        List<String> options = new ArrayList<>();
        String correctAnswer = null;

        String[] lines = questionText.split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Remove 'a)', 'b)', 'c)', 'd)' prefixes and add the option text
            String optionText = line.replaceAll("^[a-d]\\)", "").trim();
            options.add(optionText);

            // Identify the correct answer based on the first option
            if (line.startsWith("a)")) {
                correctAnswer = optionText;
            }
        }

        if (options.size() < 4) {
            // Ensure we have at least 4 options, or else return null
            return null;
        }

        // Set options and correct answer
        questionAddDTO.setOption1(options.get(0));
        questionAddDTO.setOption2(options.get(1));
        questionAddDTO.setOption3(options.get(2));
        questionAddDTO.setOption4(options.get(3));
        questionAddDTO.setRightAnswer(correctAnswer);

        return questionAddDTO;
    }
}
