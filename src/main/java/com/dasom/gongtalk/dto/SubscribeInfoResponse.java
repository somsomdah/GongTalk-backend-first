package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class SubscribeInfoResponse {

    private String type;
    private Board board;
    private List<String> keywordContents;
    private boolean notifyAll;


    public static List<SubscribeInfoResponse> fromSubscribes(List<Subscribe> subscribes){
        List<SubscribeInfoResponse> response = new ArrayList<>();
        SubscribeInfoResponse typeCK = new SubscribeInfoResponse("CK", null, new ArrayList<>(), false);

        for (Subscribe subscribe : subscribes){
            String keywordContent = subscribe.getKeyword().getContent();
            if (subscribe.getType() == "CK"){
                typeCK.getKeywordContents().add(keywordContent);
            }else{

                Stream<SubscribeInfoResponse> subscribeInfoResponseStream =
                        response.stream().filter(r->r.getBoard().equals(subscribe.getBoard()));

                if (subscribeInfoResponseStream.findAny().isEmpty()){
                    SubscribeInfoResponse subscribeInfoResponseNew = new SubscribeInfoResponse(subscribe.getType(), subscribe.getBoard(), new ArrayList<>(),false);
                    response.add(subscribeInfoResponseNew);
                }

                SubscribeInfoResponse subscribeInfoResponse = subscribeInfoResponseStream.findFirst().get();

                if(subscribe.getType()=="BK"){
                    subscribeInfoResponse.getKeywordContents().add(keywordContent);
                }else{
                    subscribeInfoResponse.setNotifyAll(true);
                }
            }
        }

        response.add(typeCK);

        return response;
    }

}
