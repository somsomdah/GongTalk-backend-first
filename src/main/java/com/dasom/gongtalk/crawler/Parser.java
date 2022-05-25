package com.dasom.gongtalk.crawler;


import com.dasom.gongtalk.domain.CrawlingInfo;
import com.dasom.gongtalk.domain.Post;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

                String postUrl = row.select(info.getBoardRowItemSelector()).first().absUrl("href");
                postUrls.add(getValidUrl(postUrl));
            }catch (Exception e){
                System.out.println("[Exception] Parser - extractPostUrls : "+e.toString());
                break;
            }
        }
        return postUrls;
    }

    public static String getValidUrl(String urlString) throws MalformedURLException, URISyntaxException {
        URL url = new URL(urlString);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        return uri.toURL().toString();
    }

    // TODO : Body로 값을 넘겨야 할 경우 extractBodies 함수 만들기

    Parser(Post post) throws IOException {
        this.info = post.getBoard().getCrawlingInfo();
        this.doc = Jsoup.connect(post.getUrl()).get();
        System.out.println("===========================");
        System.out.println(post.getUrl());
        System.out.println("===========================");
    }

    public String extractTitle(){
        String titleSelector = info.getPostTitleSelector();
        Element titleElement = this.doc.select(titleSelector).first();
        assert  titleElement != null;
        return Jsoup.parse(titleElement.toString()).text();
    }

    public String extractContent(){
        String contentSelector = info.getPostContentSelector();
        Element contentElement = this.doc.select(contentSelector).first();
        assert contentElement != null;
        return contentElement.toString();
    }

    public String extractCategory(){
        String categorySelector = info.getPostCategorySelector();
        if (categorySelector.isEmpty() || categorySelector.isBlank()){
            return "";
        }
        Element categoryElement = this.doc.select(categorySelector).first();
        assert categoryElement != null;
        String categoryString = Jsoup.parse(categoryElement.toString()).text();
        categoryString = categoryString.replaceAll("[^\uAC00-\uD7A30-9a-zA-Z]", "");
        return categoryString;
    }

    public String extractWriter(){
        String writerSelector = info.getPostWriterSelector();
        Element writerElement = this.doc.select(writerSelector).first();
        assert writerElement != null;
        return Jsoup.parse(writerElement.toString()).text();
    }

    public LocalDate extractDate(String datePattern){
        String dateSelector = info.getPostDateSelector();
        Element dateElement = this.doc.select(dateSelector).first();

        assert dateElement != null;

        String dateString = Jsoup.parse(dateElement.toString()).text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(dateString, formatter);
    }


}
