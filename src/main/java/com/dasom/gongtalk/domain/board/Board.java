package com.dasom.gongtalk.domain.board;

import com.dasom.gongtalk.crawler.Parser;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch= FetchType.EAGER)
    private School school;


    //======================== Data For Crawling ========================//
    private String baseUrl;
    private String contentSelector;
    private String titleSelector;
    private String categorySelector;
    private String writerSelector;
    private String dateSelector;
    private String datePattern;
    private Integer lastPostNum;



}
