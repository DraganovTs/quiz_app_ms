package com.homecode.controller;

import com.homecode.service.QuestionSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/pdf")
public class SeedController {

    private final QuestionSeedService questionSeedService;

    @Autowired
    public SeedController(QuestionSeedService questionSeedService) {
        this.questionSeedService = questionSeedService;
    }

    @PostMapping("/convert")
    public String convertPdfToString(@RequestParam("file") MultipartFile pdfFile) {
        return questionSeedService.seedQuestionsFromPdf(pdfFile);
    }
}