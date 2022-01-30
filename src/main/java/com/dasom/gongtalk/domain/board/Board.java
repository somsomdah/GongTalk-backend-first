package com.dasom.gongtalk.domain.board;

import com.dasom.gongtalk.domain.school.School;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({
        "baseUrl", "contentSelector", "titleSelector", "categorySelector",
        "writerSelector", "dateSelector", "datePattern", "lastPostNum"})
@Table(name="board", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "school_id"})})
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
