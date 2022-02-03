package com.dasom.gongtalk.crawler;


import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.CrawlingInfo;
import com.dasom.gongtalk.domain.Post;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class Parser {

    private CrawlingInfo info;
    private Document doc;

    Parser (CrawlingInfo info) throws IOException {
        this.info = info;
        this.doc = Jsoup.connect(info.getBoardUrl()).get();
    }

    public List<String> extractPostUrls(){
        Elements rows = this.doc.select(info.getBoardRowSelector());
        List<String> postUrls = new ArrayList<>();
        for(Element row: rows){
            try {
                String postUrl = row.select(info.getBoardRowItemSelector()).attr(info.getBoardRowItemAttr());
                String postFullUrl = String.format("%s/%s",
                        info.getPostBaseUrl(), postUrl);

                postUrls.add(postFullUrl);
            }catch (Exception e){
                System.out.println("[Exception] Parser - extractPostUrls : "+e.toString());
                break;
            }
        }
        return postUrls;
    }

    Parser(Post post) throws IOException {
        this.info = post.getBoard().getCrawlingInfo();
        this.doc = Jsoup.connect(post.getUrl()).get();
    }

    public String extractTitle(){
        String titleSelector = info.getPostTitleSelector();
        Elements titleElement = this.doc.select(titleSelector);
        return Jsoup.parse(titleElement.toString()).text();
    }

    public String extractContent(){
        String contentSelector = info.getPostContentSelector();
        Elements contentElement = this.doc.select(contentSelector);
        return contentElement.toString();
    }

    public String extractCategory(){
        String categorySelector = info.getPostCategorySelector();
        Elements categoryElement = this.doc.select(categorySelector);
        String categoryString = Jsoup.parse(categoryElement.toString()).text();
        categoryString = categoryString.replace("[","").replace("]","");
        return categoryString;
    }

    public String extractWriter(){
        String writerSelector = info.getPostWriterSelector();
        Elements writerElement = this.doc.select(writerSelector);
        return Jsoup.parse(writerElement.toString()).text();
    }

    public LocalDate extractDate(String datePattern){
        String dateSelector = info.getPostDateSelector();
        Elements dateElement = this.doc.select(dateSelector);
        String dateString = Jsoup.parse(dateElement.toString()).text();

        String[] splitDate = dateString.split("[-./]");

        if (splitDate[0].length()==2){
            splitDate[0] = Integer.toString(LocalDate.now().getYear());
            dateString = String.join("",splitDate);
            datePattern = "yyyyMMdd";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(dateString, formatter);
    }


}
