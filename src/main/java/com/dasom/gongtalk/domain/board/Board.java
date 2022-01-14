package com.dasom.gongtalk.domain.board;

import com.dasom.gongtalk.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String url;

    @ManyToOne(fetch= FetchType.LAZY)
    private School school;



}
