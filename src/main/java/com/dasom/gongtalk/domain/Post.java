package com.dasom.gongtalk.domain;

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
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch=FetchType.EAGER)
    private Board board;

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

    @NotNull
    @Column(columnDefinition = "varchar(1000) CHARACTER SET ascii", unique = true)
    private String url;

    private String httpBody;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Alarm> alarms;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Scrap> scraps;


    public Post(Board board, String url){
        this.board = board;
        this.url = url;
        this.isModified = false;
        this.isDeleted = false;
    }


}
