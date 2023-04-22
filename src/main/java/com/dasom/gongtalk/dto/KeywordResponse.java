package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Keyword;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class KeywordResponse {

    private Integer id;
    private String content;

    public static KeywordResponse fromKeyword(Keyword keyword) {
        return new KeywordResponse(keyword.getId(), keyword.getContent());
    }

    public static List<KeywordResponse> fromKeywords(List<Keyword> keywords) {
        List<KeywordResponse> response = new ArrayList<>();

        for (Keyword keyword : keywords) {
            response.add(KeywordResponse.fromKeyword(keyword));
        }

        return response;
    }

}
