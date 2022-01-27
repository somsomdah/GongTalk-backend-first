package com.dasom.gongtalk.dto;

import lombok.Getter;

@Getter
public class SubscribeRequest {
    private String type;
    private Integer boardId;
    private String keywordContent;
}
