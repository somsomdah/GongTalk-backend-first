package com.dasom.gongtalk.domain.member;

import com.dasom.gongtalk.domain.board.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class MemberBoard {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;

    private boolean isAlarmable;
    private Date timeCreated;



}
