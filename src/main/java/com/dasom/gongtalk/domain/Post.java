package com.dasom.gongtalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"content", "keywords", "alarms", "scraps"})
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "varchar(50)")
    private String writer;

    @Column(columnDefinition = "varchar(50)")
    private String category;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @NotNull
    private String date;

    @NotNull
    @Column(columnDefinition = "varchar(1000) CHARACTER SET ascii", unique = true)
    private String url;

    private String httpBody;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Alarm> alarms;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Scrap> scraps;

    Post(Board board, String url) {
        this.setBoard(board);
        this.setUrl(url);
    }

    public static Post createPost(Board board, String url) {
        return new Post(board, url);
    }

}
