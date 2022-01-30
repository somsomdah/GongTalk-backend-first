package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.AlarmRepository;
import com.dasom.gongtalk.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final SubscribeRepository subscribeRepository;

    public Alarm getFromId(Integer id){
        Optional<Alarm> alarm = alarmRepository.findById(id);

        if(alarm.isEmpty()){
            throw new ResourceNotFoundException("Alarm", "id", id);
        }

        return alarm.get();
    }

    public void save(Post post){
        try{
            List<Keyword> keywords = post.getKeywords();
            Board board = post.getBoard();



            List<Subscribe> subscribeTypeBk = subscribeRepository.findAllByTypeAndBoardAndKeywordIn("BK", board, keywords);
            List<Subscribe> subscribeTypeCk = subscribeRepository.findAllByTypeAndBoardAndKeywordIn("CK", null, keywords);
            List<Subscribe> subscribeTypeB = subscribeRepository.findAllByTypeAndBoardAndKeyword("B", board, null);

            List<Alarm> alarms = new ArrayList<>();

            if (!subscribeTypeB.isEmpty()) {
                for (Subscribe s : subscribeTypeB) {
                    if (s.getBoard().equals(board)) {
                        Alarm alarm = new Alarm(s.getUser(), post);
                        alarms.add(alarm);
                    }
                }
            }

            if(!subscribeTypeBk.isEmpty()){
                for (Subscribe s : subscribeTypeBk) {
                    if (s.getBoard().equals(board) && keywords.contains(s.getKeyword())) {
                        Alarm alarm = new Alarm(s.getUser(), post);
                        alarms.add(alarm);
                    }
                }
            }


            if(!subscribeTypeCk.isEmpty()){
                for (Subscribe s : subscribeTypeCk) {
                    if (keywords.contains(s.getKeyword())) {
                        Alarm alarm = new Alarm(s.getUser(), post);
                        alarms.add(alarm);
                    }
                }
            };

            alarmRepository.saveAll(alarms);

        }catch (Exception ex){
            System.out.println("[Exception] AlarmService : save : "+ex.toString());
        }

    }

}
