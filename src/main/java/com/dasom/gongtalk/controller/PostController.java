package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.dto.PostListResponse;
import com.dasom.gongtalk.dto.PostResponse;
import com.dasom.gongtalk.repository.PostRepository;
import com.dasom.gongtalk.service.BoardService;
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
    private final BoardService boardService;

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getOnePost(@PathVariable Long id) {
        Post post = postService.getFromId(id);
        PostResponse response = PostResponse.fromPost(post);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostListResponse>> getPosts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "100") Integer size,
                                                           @RequestParam(required = false) Long boardId,
                                                           @RequestParam(required = false) List<String> keywordContent
    ) {
        List<Post> posts;
        if (boardId != null) {
            if (keywordContent != null) {
                posts = postRepository.findAllByBoardIdAndKeywordsContentIn(boardId, keywordContent);
            } else {
                posts = postService.getPostsFromBoard(boardService.getFromId(boardId), page, size);
            }

        } else {
            if (keywordContent != null) {
                posts = postRepository.findAllByKeywordsContentIn(keywordContent);
            } else {
                posts = (List<Post>) postRepository.findAll();
            }
        }

        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
