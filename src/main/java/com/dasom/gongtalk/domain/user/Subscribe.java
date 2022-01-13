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
public class Subscribe {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch= FetchType.EAGER)
    private Member member;
    @ManyToOne(fetch= FetchType.EAGER)
    private Board board;
    @ManyToOne(fetch= FetchType.EAGER)
    private Keyword keyword;

    private String type; // B(for 'Board') 또는 K (for 'Keyword')


}
