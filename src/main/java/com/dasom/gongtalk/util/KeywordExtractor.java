package com.dasom.gongtalk.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeywordExtractor {

    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    public List<String> extract(String string){
        KomoranResult result = komoran.analyze(string);

        List<String> keywords = new ArrayList<>();
        List<Token> tokenList = result.getTokenList();

        for (Token token : tokenList){
            String keywordString = token.getMorph();
            if (keywords.contains(keywordString)){
                continue;
            }
            keywords.add(keywordString);
        }

        String[] keywordStrings = string.split(" ");
        for (String keywordString : keywordStrings){
            if (keywords.contains(keywordString)){
                continue;
            }
            keywords.add(keywordString);
        }

        return keywords;

    }

}
