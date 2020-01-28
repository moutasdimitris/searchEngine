package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

public class Multithreading implements Callable <HashSet<String>> {
    private  String url;
    private  HashSet<String> link=new HashSet<>();


    Multithreading(String url) {
        this.url=url;
    }


    @Override
    public HashSet<String> call() {
        try {
            URL url1=new URL(url);
            Document document = Jsoup.connect(String.valueOf(url1)).get();
            Elements linksOnPage = document.select("a[href]");
            for (Element links : linksOnPage) {
                link.add(links.attr("abs:href"));
            }
        }catch (IOException | RejectedExecutionException | IllegalArgumentException ignored){
        }
        return link;
    }
}


