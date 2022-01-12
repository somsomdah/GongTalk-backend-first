package com.dasom.gongtalk.domain.post;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.member.Scrap;
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
    @GeneratedValue
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    private Board board;

    @NotNull
    private int post_num;

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
    private String content;
    @NotNull
    private LocalDate date;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean isModified;

//    @OneToMany
//    private List<Image> images;
//    @OneToMany
//    private List<File> files;
//    @OneToMany
//    private List<Pyo> pyos;
//
//    @OneToMany
//    private List<Scrap> scraps;

//    @OneToMany
//    private List<PostKeyword> postKeywords;

}
