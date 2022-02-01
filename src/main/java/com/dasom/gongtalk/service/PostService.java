package com.dasom.gongtalk.service;


import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.AlarmRepository;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.repository.PostRepository;
import com.dasom.gongtalk.repository.SubscribeRepository;
import com.dasom.gongtalk.util.KeywordExtractor;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final KeywordService keywordService;

    public Post getFromId(Integer id){
        Optional<Post> post = postRepository.findById(id);
        try{
            return post.get();
        }catch (Exception e){
            throw new ResourceNotFoundException(e.toString(), "post", "id", id);
        }
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

    public List<Post> getPostsFromBoard(Board board, int page, int size){
        Pageable pageable= PageRequest.of(page,size, Sort.by("date"));
        return postRepository.findAllByBoard(board, pageable);
    }
}
