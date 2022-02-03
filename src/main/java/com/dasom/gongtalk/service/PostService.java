package com.dasom.gongtalk.service;


import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.domain.PostKeyword;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.PostKeywordRepository;
import com.dasom.gongtalk.repository.PostRepository;
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
    private final PostKeywordRepository postKeywordRepository;

    public Post getFromId(Integer id){
        Optional<Post> post = postRepository.findById(id);
        try{
            return post.get();
        }catch (Exception e){
            throw new ResourceNotFoundException(e.toString(), "post", "id", id);
        }
    }

    public List<PostKeyword> save(Post post){

        String content = post.getContent();
        String parsedText = Jsoup.parse(content).text();
        String title = post.getTitle();

        KeywordExtractor extractor = new KeywordExtractor();
        List<String> contentKeywordStings = extractor.extract(parsedText);
        List<String> titleKeywordStrings = extractor.extract(title);

        List<PostKeyword> postKeywords = new ArrayList<>();


        for (String keywordString: contentKeywordStings){
            Keyword keyword = keywordService.getOrCreateFromContent(keywordString);
            PostKeyword postKeyword = new PostKeyword(post, keyword);
            if (!postKeywords.contains(postKeyword)){
                postKeywords.add(postKeywordRepository.save(postKeyword));
            }
        }

        for (String keywordString: titleKeywordStrings){
            Keyword keyword = keywordService.getOrCreateFromContent(keywordString);
            PostKeyword postKeyword = new PostKeyword(post, keyword);
            if (!postKeywords.contains(postKeyword)){
                postKeywords.add(postKeywordRepository.save(postKeyword));
            }
        }

        return postKeywords;

    }

    public List<Post> getPostsFromBoard(Board board, int page, int size){
        Pageable pageable= PageRequest.of(page,size, Sort.by("date"));
        return postRepository.findAllByBoard(board, pageable);
    }
}
