package com.dasom.gongtalk.domain.user;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name="subscribe", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "board_id", "keyword_id"}))
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch= FetchType.LAZY)
    @NotNull
    private User user;

    @ManyToOne(fetch= FetchType.EAGER)
    private Board board;

    @ManyToOne(fetch= FetchType.EAGER)
    private Keyword keyword;


    private String type; // B(for 'Board') 또는 CK (for 'CommonKeyword') 또는 BK("BoardKeyword")

    public Subscribe(User user, Board board, Keyword keyword, String type){
        this.user = user;
        this.board = board;
        this.keyword = keyword;
        this.type = type;
    }


}
