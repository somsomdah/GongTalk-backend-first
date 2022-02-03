package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Subscribe;
import lombok.Getter;

@Getter
public class SubscribeRequest {
    private Subscribe.Type type;
    private Integer boardId;
    private String keywordContent;
}
