package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.user.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SubscribeInfoResponse {

    private String type;
    private Board board;
    private List<Keyword> keywords;
    private boolean notifyAll = false;


    public static List<SubscribeInfoResponse> fromSubscribes(List<Subscribe> subscribes){
        List<SubscribeInfoResponse> response = new ArrayList<>();
        SubscribeInfoResponse typeCK = new SubscribeInfoResponse("CK", null, new ArrayList<>(), false);

        for (Subscribe subscribe : subscribes){

            if (subscribe.getType().equals("CK")){
                typeCK.getKeywords().add(subscribe.getKeyword());
            }else{

                if (response
                        .stream()
                        .filter(r->r.getBoard().equals(subscribe.getBoard()))
                        .findAny()
                        .isEmpty()){
                    SubscribeInfoResponse subscribeInfoResponseNew = new SubscribeInfoResponse(subscribe.getType(), subscribe.getBoard(), new ArrayList<>(),false);
                    response.add(subscribeInfoResponseNew);
                }

                SubscribeInfoResponse subscribeInfoResponse = response
                        .stream()
                        .filter(r->r.getBoard().equals(subscribe.getBoard()))
                        .findFirst().get();

                if (subscribe.getType().equals("B")){
                    subscribeInfoResponse.setNotifyAll(true);
                    continue;
                }

                if(subscribe.getType().equals("BK")){
                    subscribeInfoResponse.getKeywords().add(subscribe.getKeyword());
                }
            }
        }

        response.add(typeCK);

        return response;
    }

}
