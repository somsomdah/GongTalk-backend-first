package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.CrawlingInfo;
import com.dasom.gongtalk.domain.Post;
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

public interface Parser {

    public List<String> extractPostUrls() throws IOException;
    public String extractTitle() throws IOException;
    public String extractContent() throws IOException;
    public String extractCategory() throws IOException;
    public String extractWriter() throws IOException;
    public LocalDate extractDate(String datePattern) throws IOException;

}
