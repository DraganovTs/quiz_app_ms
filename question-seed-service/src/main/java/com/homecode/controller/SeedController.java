package com.homecode.controller;

import com.homecode.service.SeedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class SeedController {

    private final SeedService seedService;

    public SeedController(SeedService seedService) {
        this.seedService = seedService;
    }


    @PostMapping("/convert")
    public String convertPdfToString(@RequestParam("file") MultipartFile pdfFile) throws IOException {
        return seedService.convertPdfToString(pdfFile);
    }
}
