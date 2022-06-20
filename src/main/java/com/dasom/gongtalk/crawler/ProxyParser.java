package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.CrawlingInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ProxyParser implements Parser{

    private CrawlingInfo info;
    private TargetParser targetParser;

    ProxyParser(CrawlingInfo info){
        this.info = info;
    }

    void connect() throws IOException {
        if (targetParser == null)  this.targetParser = new TargetParser(this.info);
    }

    public List<String> extractPostUrls() throws IOException {
        connect();
        return targetParser.extractPostUrls();
    };
    public String extractTitle() throws IOException {
        connect();
        return targetParser.extractTitle();
    }
    public String extractContent() throws IOException {
        connect();
        return targetParser.extractContent();
    }
    public String extractCategory() throws IOException{
        connect();
        return targetParser.extractCategory() ;
    }
    public String extractWriter() throws IOException{
        connect();
        return targetParser.extractWriter();
    }
    public LocalDate extractDate(String datePattern) throws IOException{
        connect();
        return targetParser.extractDate(datePattern);
    }


}
