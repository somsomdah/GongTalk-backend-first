package com.dasom.gongtalk.domain.post;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"content", "keywords"})
@Table(name="post", uniqueConstraints = @UniqueConstraint(columnNames = {"board_id","post_num"}))
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.EAGER)
    private Board board;

    @NotNull
    private Integer postNum;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "varchar(50) default '관리자'")
    private String writer;

    @Column(columnDefinition = "varchar(50) default '일반'")
    private String category;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @NotNull
    private LocalDate date;

    @NotNull
    private boolean isDeleted;

    @NotNull
    private boolean isModified;

    @ManyToMany
    private List<Keyword> keywords;

    @Transient
    private String url;

    public String getUrl() {

        return this.getBoard().getBaseUrl() + this.getPostNum();
    }

    public Post(Board board, Integer postNum){
        this.board = board;
        this.postNum = postNum;
        this.isModified = false;
        this.isDeleted = false;
    }

}
