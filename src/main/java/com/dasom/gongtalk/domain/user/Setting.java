package com.dasom.gongtalk.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean push;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean vibration;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean silence;

}
