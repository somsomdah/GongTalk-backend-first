package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public Keyword getOrCreateFromContent(String content){
        Optional<Keyword> keyword = keywordRepository.findByContent(content);
        if (keyword.isEmpty()){
            Keyword newKeyword = new Keyword();
            newKeyword.setContent(content);
            return keywordRepository.save(newKeyword);
        }

        return keyword.get();
    }
}
