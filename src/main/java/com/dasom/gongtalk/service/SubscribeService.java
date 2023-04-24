package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Subscribe;
import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.dto.SubscribeDeleteRequest;
import com.dasom.gongtalk.dto.SubscribeRequest;
import com.dasom.gongtalk.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final BoardService boardService;
    private final KeywordService keywordService;


    public Subscribe save(User user, SubscribeRequest subscribeRequest) {
        Board board = null;
        if (subscribeRequest.getBoardId() != null) {
            board = boardService.getFromId(subscribeRequest.getBoardId());
        }

        Keyword keyword = null;
        if (subscribeRequest.getKeywordContent() != null) {
            keyword = keywordService.getOrCreateFromContent(subscribeRequest.getKeywordContent());
        }

        Subscribe.Type type = subscribeRequest.getType();
        Subscribe newSubscribe = new Subscribe(user, board, keyword, type);
        return subscribeRepository.save(newSubscribe);
    }

    public void deleteSubscribe(User user, SubscribeDeleteRequest subscribeDeleteRequest) {
        Long boardId = subscribeDeleteRequest.getBoardId();
        Long keywordId = subscribeDeleteRequest.getKeywordId();
        Subscribe.Type type = Subscribe.Type.valueOf(subscribeDeleteRequest.getType());

        subscribeRepository.deleteAll(subscribeRepository.findAllByUserAndTypeAndBoardIdAndKeywordId(user, type, boardId, keywordId));

    }
}
