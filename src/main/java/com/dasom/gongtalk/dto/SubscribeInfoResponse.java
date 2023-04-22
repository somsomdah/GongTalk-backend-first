package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SubscribeInfoResponse {

    private Subscribe.Type type;
    private Board board;
    private List<Keyword> keywords;
    private boolean notifyAll = false;


    public static List<SubscribeInfoResponse> fromSubscribes(List<Subscribe> subscribes) {
        List<SubscribeInfoResponse> response = new ArrayList<>();
        SubscribeInfoResponse typeCK = new SubscribeInfoResponse(Subscribe.Type.KEYWORD_COMMON, null, new ArrayList<>(), false);

        for (Subscribe subscribe : subscribes) {

            if (subscribe.getType().equals(Subscribe.Type.KEYWORD_COMMON)) {
                typeCK.getKeywords().add(subscribe.getKeyword());
            } else {

                if (response
                        .stream()
                        .filter(r -> r.getBoard().equals(subscribe.getBoard()))
                        .findAny()
                        .isEmpty()) {
                    SubscribeInfoResponse subscribeInfoResponseNew = new SubscribeInfoResponse(subscribe.getType(), subscribe.getBoard(), new ArrayList<>(), false);
                    response.add(subscribeInfoResponseNew);
                }

                SubscribeInfoResponse subscribeInfoResponse = response
                        .stream()
                        .filter(r -> r.getBoard().equals(subscribe.getBoard()))
                        .findFirst().get();

                if (subscribe.getType().equals(Subscribe.Type.BOARD)) {
                    subscribeInfoResponse.setNotifyAll(true);
                    continue;
                }

                if (subscribe.getType().equals(Subscribe.Type.KEYWORD_BOARD)) {
                    subscribeInfoResponse.getKeywords().add(subscribe.getKeyword());
                }
            }
        }

        response.add(typeCK);

        return response;
    }

}
