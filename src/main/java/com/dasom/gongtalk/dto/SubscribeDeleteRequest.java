package com.dasom.gongtalk.dto;

import lombok.Getter;

@Getter
public class SubscribeDeleteRequest {
    private String type;
    private Integer boardId;
    private Integer keywordId;
}
