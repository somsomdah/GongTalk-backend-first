package com.dasom.gongtalk.domain.post;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.service.BoardService;
import com.dasom.gongtalk.service.PostService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    private Board board;

    @NotNull
    private Integer postNum;

    @NotNull
    private String source;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "varchar(50) default '관리자'")
    private String writer;

    @NotNull
    @Column(columnDefinition = "varchar(50) default '일반'")
    private String category;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @NotNull
    private LocalDate date;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean isModified;

    @ManyToMany
    private List<Keyword> keywords;


}
