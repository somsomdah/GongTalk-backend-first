package com.dasom.gongtalk.domain.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String device_num;

    @OneToOne
    private Setting setting;

}

