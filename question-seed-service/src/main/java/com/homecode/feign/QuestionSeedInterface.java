package com.homecode.feign;

import com.homecode.model.QuestionAddDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("QUESTION-SERVICE")
public interface QuestionSeedInterface {

    @PostMapping("questions/create")
    public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionAddDTO questionAddDTO);
}
