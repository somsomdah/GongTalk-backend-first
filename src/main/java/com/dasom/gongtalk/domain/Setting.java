package com.dasom.gongtalk.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private boolean push;

    @NotNull
    private boolean vibration;

    @NotNull
    private boolean silence;

    public Setting(){
        this.push = true;
        this.vibration = false;
        this.silence = false;
    }

}