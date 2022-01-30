package com.dasom.gongtalk.crawler;


import com.dasom.gongtalk.domain.board.Board;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Parser {

    private final Board board;
    private final Document doc;

    Parser(Board board, String pageUrl) throws IOException {
        this.board = board;
        this.doc = Jsoup.connect(pageUrl).get();
    }

    public String extractTitle(){
        String titleSelector = board.getTitleSelector();
        Elements titleElement = this.doc.select(titleSelector);
        return Jsoup.parse(titleElement.toString()).text();
    }

    public String extractContent(){
        String contentSelector = board.getContentSelector();
        Elements contentElement = this.doc.select(contentSelector);
        return contentElement.toString();
    }

    public String extractCategory(){
        String categorySelector = board.getCategorySelector();
        Elements categoryElement = this.doc.select(categorySelector);
        String categoryString = Jsoup.parse(categoryElement.toString()).text();
        categoryString = categoryString.replace("[","").replace("]","");
        return categoryString;
    }

    public String extractWriter(){
        String writerSelector = board.getWriterSelector();
        Elements writerElement = this.doc.select(writerSelector);
        return Jsoup.parse(writerElement.toString()).text();
    }

    public LocalDate extractDate(String datePattern){
        String dateSelector = board.getDateSelector();
        Elements dateElement = this.doc.select(dateSelector);
        String dateString = Jsoup.parse(dateElement.toString()).text();

        String[] splitDate = dateString.split("[-./]");
        String.join("", splitDate);

        if (splitDate.length>3 && splitDate[0].length()==2){
            splitDate[0] = Integer.toString(LocalDate.now().getYear());
            dateString = String.join("",splitDate);
            datePattern = "yyyyMMdd";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(dateString, formatter);
    }


}
