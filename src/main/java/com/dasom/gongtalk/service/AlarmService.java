package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.*;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.SqlException;
import com.dasom.gongtalk.exception.UserForbiddenException;
import com.dasom.gongtalk.repository.AlarmRepository;
import com.dasom.gongtalk.repository.KeywordRepository;
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
    private final KeywordRepository keywordRepository;

    public Alarm getFromId(Integer id){
        Optional<Alarm> alarm = alarmRepository.findById(id);
        try{
            return alarm.get();
        }catch (Exception e){
            throw new ResourceNotFoundException("alarm", "id", id, e.toString());
        }
    }

    public void save(Post post){

        // TODO : FCM NOTIFICATION
        try{
            List<Keyword> keywords = keywordRepository.findAllByPost(post);
            Board board = post.getBoard();

            List<Subscribe> subscribeTypeBk = subscribeRepository.findAllByTypeAndBoardAndKeywordIn(Subscribe.Type.KEYWORD_BOARD, board, keywords);
            List<Subscribe> subscribeTypeCk = subscribeRepository.findAllByTypeAndBoardAndKeywordIn(Subscribe.Type.KEYWORD_COMMON, null, keywords);
            List<Subscribe> subscribeTypeB = subscribeRepository.findAllByTypeAndBoardAndKeyword(Subscribe.Type.BOARD, board, null);

            List<Alarm> alarms = new ArrayList<>();

            if (!subscribeTypeB.isEmpty()) {
                for (Subscribe s : subscribeTypeB) {
                    if (s.getBoard().getId().equals(board.getId())) {
                        Alarm alarm = new Alarm(s.getUser(), post);
                        alarms.add(alarm);
                    }
                }
            }

            if(!subscribeTypeBk.isEmpty()){
                for (Subscribe s : subscribeTypeBk) {
                    if (s.getBoard().getId().equals(board.getId()) && keywords.contains(s.getKeyword())) {
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
            throw new SqlException("Alarm create error", ex.toString());
        }

    }

    public Alarm update(Integer alarmId, Alarm newAlarm){
        boolean isRead = newAlarm.isRead();
        Alarm alarm = getFromId(alarmId);
        alarm.setRead(isRead);
        return  alarmRepository.save(alarm);
    }

    public void checkAuthority(User user, Alarm alarm){
        if (!user.equals(alarm.getUser())){
            throw new UserForbiddenException(String.format("The user has no authority to alarm id %d",alarm.getId()));
        }
    }

    public void checkAllAuthorities(User user, List<Alarm> alarms){
        for(Alarm alarm : alarms){
            checkAuthority(user, alarm);
        }
    }

}
