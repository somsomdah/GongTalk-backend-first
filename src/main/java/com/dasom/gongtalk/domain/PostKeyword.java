package com.dasom.gongtalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name="post_keyword", uniqueConstraints = @UniqueConstraint(columnNames = {"post_id","keyword_id"}))
public class PostKeyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private Keyword keyword;

    public PostKeyword(Post post, Keyword keyword){
        this.post = post;
        this.keyword = keyword;
    }
}
