package com.dasom.gongtalk.domain.member;

import com.dasom.gongtalk.domain.post.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;


}
