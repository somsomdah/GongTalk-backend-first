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

        List<Keyword> keywords = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(extractedText," ");
        while (tokenizer.hasMoreTokens()){
            Keyword keyword = keywordService.getOrCreateFromContent(tokenizer.nextToken());;
            keywords.add(keyword);
        }

        post.setKeywords(keywords);
        return postRepository.save(post);
    }
}
