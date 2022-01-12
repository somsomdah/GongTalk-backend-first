package com.dasom.gongtalk.domain.member;

import com.dasom.gongtalk.domain.post.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Setting {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Member member;

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
