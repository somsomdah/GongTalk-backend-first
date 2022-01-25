package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.dto.PostRequest;
import com.dasom.gongtalk.dto.PostResponse;
import com.dasom.gongtalk.repository.PostRepository;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/posts")
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getOnePost(@PathVariable Integer id){
        Post post = postService.getFromId(id);
        PostResponse response = PostResponse.fromEntity(post);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(params = {"search-keyword"})
    public ResponseEntity<List<Post>> getAllPostsFromKeywords(@RequestParam(value = "search-keyword") List<String> searchKeywords){
        List<Post> posts = postRepository.findAllByKeywordsContentIn(searchKeywords);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PostMapping
    public ResponseEntity<Post> createOnePost(@RequestBody PostRequest postRequest){
        Post post = postService.save(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

}
