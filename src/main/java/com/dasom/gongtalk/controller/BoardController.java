package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.domain.School;
import com.dasom.gongtalk.dto.PostListResponse;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.repository.PostRepository;
import com.dasom.gongtalk.repository.SchoolRepository;
import com.dasom.gongtalk.service.BoardService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/boards")
public class BoardController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final SchoolRepository schoolRepository;

    @GetMapping
    public ResponseEntity<List<Board>> getBoards(@RequestParam(required = false) int schoolId,
                                                 @RequestParam(required = false, defaultValue ="0") int page,
                                                 @RequestParam(required = false, defaultValue = "3") int size
                                                 ){
        List<Board> boards = boardService.getAllFromSchoolId(schoolId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }

    @GetMapping(value="{id}/posts")
    public ResponseEntity<List<PostListResponse>> getPosts(@PathVariable Integer id,
                                                           @RequestParam(required = false, defaultValue ="0") int page,
                                                           @RequestParam(required = false, defaultValue = "3") int size){
        List<Post> posts = postService.getPostsFromBoard(boardService.getFromId(id),page, size );
        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "{id}/search", params = {"keyword"})
    public ResponseEntity<List<PostListResponse>> getAllPostsFromKeywords(@PathVariable Integer id,
                                                                          @RequestParam(value = "keyword") List<String> searchKeywords){
        Board board = boardService.getFromId(id);
        List<Post> posts = postRepository.findAllByBoardAndKeywordsContentIn(board,searchKeywords);
        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "schools")
    public ResponseEntity<List<School>> getAllSchools(){
        List<School> schools = schoolRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(schools);
    }
}
