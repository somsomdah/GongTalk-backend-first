package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public Keyword getFromId(Integer id){
        Optional<Keyword> keyword = keywordRepository.findById(id);
        try {return keyword.get();}
        catch (Exception e){
            throw new ResourceNotFoundException(e.toString(), "keyword", "id", id);
        }
    }

    public Keyword getOrCreateFromContent(String content){
        Optional<Keyword> keyword = keywordRepository.findByContent(content);
        if (keyword.isEmpty()){
            Keyword newKeyword = new Keyword(content);
            return keywordRepository.save(newKeyword);
        }

        return keyword.get();
    }
}
