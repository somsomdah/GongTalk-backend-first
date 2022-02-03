package com.dasom.gongtalk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name="user_board", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","board_id"}))
public class UserBoard {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

    public UserBoard(User user, Board board){
        this.user = user;
        this.board = board;
    }
}
