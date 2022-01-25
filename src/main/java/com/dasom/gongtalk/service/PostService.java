package com.dasom.gongtalk.service;


import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.dto.PostRequest;
import com.dasom.gongtalk.dto.PostResponse;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.repository.KeywordRepository;
import com.dasom.gongtalk.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardService boardService;
    private final KeywordService keywordService;

    public Post getFromId(Integer id){
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()){
            throw new ResourceNotFoundException("Post", "id", id);
        }

        return post.get();
    }

    public Post save(PostRequest postRequest){
        Post post = postRequest.toPost();
        post.setBoard(boardService.getFromId(postRequest.getBoardId()));

        String content = post.getContent();
        String extractedText = Jsoup.parse(content).text();
        String title = post.getTitle();

        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
        KomoranResult resultFromContent = komoran.analyze(extractedText);
        KomoranResult resultFromTitle = komoran.analyze(title);

        List<Keyword> keywords = new ArrayList<>();
        List<Token> tokenListFromContent = resultFromContent.getTokenList();
        List<Token> tokenListFromTitle = resultFromTitle.getTokenList();

        for (Token token : tokenListFromContent){
            String keywordContent = token.getMorph();
            Keyword keyword = keywordService.getOrCreateFromContent(keywordContent);

            if (!keywords.contains(keyword)){
                keywords.add(keyword);
            }
        }

        for (Token token : tokenListFromTitle){
            String keywordContent = token.getMorph();
            Keyword keyword = keywordService.getOrCreateFromContent(keywordContent);

            if (!keywords.contains(keyword)){
                keywords.add(keyword);
            }
        }

        post.setKeywords(keywords);
        return postRepository.save(post);
    }
}
