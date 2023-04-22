package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/keywords")
public class KeywordController {


    private final KeywordRepository keywordRepository;

    @GetMapping("recommended")
    public ResponseEntity<List<Keyword>> getRecommendedKeywords() {
        List<Keyword> keywords = keywordRepository.findAllRecommended();
        return ResponseEntity.status(HttpStatus.OK).body(keywords);
    }

}
