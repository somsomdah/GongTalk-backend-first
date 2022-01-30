package com.dasom.gongtalk.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
