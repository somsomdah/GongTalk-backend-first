package com.dasom.gongtalk.domain.keyword;

import com.dasom.gongtalk.domain.post.PostKeyword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String content;

    @NotNull
    @Column(columnDefinition="boolean default false")
    private boolean is_default;
}
