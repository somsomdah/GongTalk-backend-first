package com.dasom.gongtalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name="subscribe", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "board_id", "keyword_id"}))
public class Subscribe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch= FetchType.EAGER)
    @NotNull
    private User user;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch= FetchType.EAGER)
    private Board board;

    @JoinColumn(name = "keyword_id")
    @ManyToOne(fetch= FetchType.EAGER)
    private Keyword keyword;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    public Subscribe(User user, Board board, Keyword keyword, Type type){
        this.user = user;
        this.board = board;
        this.keyword = keyword;
        this.type = type;
    }

    public enum Type{
        BOARD,
        KEYWORD_COMMON, //common keyword
        KEYWORD_BOARD; //board keyword
    }


}
