package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.repository.AlarmRepository;
import com.dasom.gongtalk.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final SubscribeRepository subscribeRepository;

    public void save(Post post){
        try{
            List<Keyword> keywords = post.getKeywords();
            Board board = post.getBoard();

            List<Subscribe> subscribeTypeBk = subscribeRepository.findAllByTypeAndBoardAndKeywordIn("BK", board, keywords);
            List<Subscribe> subscribeTypeCk = subscribeRepository.findAllByTypeAndBoardAndKeywordIn("CK", null, keywords);
            List<Subscribe> subscribeTypeB = subscribeRepository.findAllByTypeAndBoardAndKeywordIn("B", board, null);

            List<Alarm> alarms = new ArrayList<>();


            for (Subscribe s : subscribeTypeB) {
                if (s.getBoard() == board) {
                    Alarm alarm = new Alarm(s.getUser(), post);
                    alarms.add(alarm);
                }
            }


            for (Subscribe s : subscribeTypeBk) {
                if (s.getBoard() == board && keywords.contains(s.getKeyword())) {
                    Alarm alarm = new Alarm(s.getUser(), post);
                    alarms.add(alarm);
                }
            }

            for (Subscribe s : subscribeTypeCk) {
                if (keywords.contains(s.getKeyword())) {
                    Alarm alarm = new Alarm(s.getUser(), post);
                    alarms.add(alarm);
                }
            }

            alarmRepository.saveAll(alarms);

        }catch (Exception ex){
            System.out.println(111111);
            System.out.println(ex);
            System.out.println(111111);
        }

    }

}
