package com.dasom.gongtalk.domain.user;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch= FetchType.EAGER)
    private User user;

    @ManyToOne(fetch= FetchType.EAGER)
    private Board board;

    @ManyToOne(fetch= FetchType.EAGER)
    private Keyword keyword;

    private String type; // B(for 'Board') 또는 CK (for 'CommonKeyword') 또는 BK("BoardKeyword")


}
