package com.dasom.gongtalk.domain.post;

import com.dasom.gongtalk.domain.keyword.Keyword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class PostKeyword {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Keyword keyword;
}
