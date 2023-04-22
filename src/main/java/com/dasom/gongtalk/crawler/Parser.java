package com.dasom.gongtalk.crawler;


import com.dasom.gongtalk.domain.Board;
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

    private Board board;
    private Document doc;

    Parser(Board board) throws IOException {
        this.doc = Jsoup.connect(board.getUrl()).get();
    }
    public static Parser createParser(Board board) throws IOException {
        return new Parser(board);
    }

    public static Parser createParser(Post post) throws IOException {
        return new Parser(post.getBoard());
    }


    public List<String> extractPostUrls(){
        Elements rows = this.doc.select(board.getRowSelector());
        List<String> postUrls = new ArrayList<>();
        for(Element row: rows){
            try {

                String postUrl = row.select(board.getRowItemSelector()).first().absUrl("href");
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

    public String extractTitle(){
        String titleSelector = board.getPostTitleSelector();
        Element titleElement = this.doc.select(titleSelector).first();
        assert  titleElement != null;
        return Jsoup.parse(titleElement.toString()).text();
    }

    public String extractContent(){
        String contentSelector = board.getPostContentSelector();
        Element contentElement = this.doc.select(contentSelector).first();
        assert contentElement != null;
        return contentElement.toString();
    }

    public String extractCategory(){
        String categorySelector = board.getPostCategorySelector();
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
        String writerSelector = board.getPostWriterSelector();
        Element writerElement = this.doc.select(writerSelector).first();
        assert writerElement != null;
        return Jsoup.parse(writerElement.toString()).text();
    }

    public LocalDate extractDate(String datePattern){
        String dateSelector = board.getPostDateSelector();
        Element dateElement = this.doc.select(dateSelector).first();

        assert dateElement != null;

        String dateString = Jsoup.parse(dateElement.toString()).text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return LocalDate.parse(dateString, formatter);
    }


}
