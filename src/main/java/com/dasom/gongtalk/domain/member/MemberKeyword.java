package com.dasom.gongtalk.domain.member;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class MemberKeyword {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch= FetchType.EAGER)
    private Member member;
    @ManyToOne(fetch= FetchType.EAGER)
    private Board board;
    @ManyToOne(fetch= FetchType.EAGER)
    private Keyword keyword;

    private boolean isCommon;
    private boolean isAlarmable;
    private Date timeCreated;




}
