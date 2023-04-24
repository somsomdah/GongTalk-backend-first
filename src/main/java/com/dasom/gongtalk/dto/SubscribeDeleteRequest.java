package com.dasom.gongtalk.dto;

import lombok.Getter;

@Getter
public class SubscribeDeleteRequest {
    private String type;
    private Long boardId;
    private Long keywordId;
}
