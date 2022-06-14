package com.dasom.gongtalk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="user_board", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","board_id"}))
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer orderValue; // NOTE: order는 예약어이기 때문에 이를 컬럼명으로 하면 안됨!

    public UserBoard(User user, Board board){
        this.user = user;
        this.board = board;
    }
}
