package com.dasom.gongtalk.domain.board;

import com.dasom.gongtalk.domain.member.MemberBoard;
import com.dasom.gongtalk.domain.member.MemberKeyword;
import com.dasom.gongtalk.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String url;

    @ManyToOne(fetch= FetchType.LAZY)
    private School school;
//
//    @OneToMany
//    private List<MemberBoard> memberBoards;
//    @OneToMany
//    private List<MemberKeyword> memberKeywords;



}
