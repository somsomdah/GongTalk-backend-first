package com.dasom.gongtalk.service;


import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.dto.PostRequest;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.PostRepository;
import com.dasom.gongtalk.util.KeywordExtractor;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Post save(Post post){

        String content = post.getContent();
        String parsedText = Jsoup.parse(content).text();
        String title = post.getTitle();

        KeywordExtractor extractor = new KeywordExtractor();
        List<String> contentKeywordStings = extractor.extract(parsedText);
        List<String> titleKeywordStrings = extractor.extract(title);

        List<Keyword> keywords = new ArrayList<>();

        for (String keywordString: contentKeywordStings){
            Keyword keyword = keywordService.getOrCreateFromContent(keywordString);
            if (!keywords.contains(keyword)){
                keywords.add(keyword);
            }
        }

        for (String keywordString: titleKeywordStrings){
            Keyword keyword = keywordService.getOrCreateFromContent(keywordString);
            if (!keywords.contains(keyword)){
                keywords.add(keyword);
            }
        }

        post.setKeywords(keywords);
        return postRepository.save(post);
    }
}
