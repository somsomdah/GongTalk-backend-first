package com.dasom.gongtalk.domain.school;

import com.dasom.gongtalk.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;
    private String campus;
//
//    @OneToMany
//    private List<Board> boards;
}
