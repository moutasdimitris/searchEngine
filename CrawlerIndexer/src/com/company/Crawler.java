package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.*;

public class Crawler {

    public HashSet<String> getLinks() {
        return links;
    }

    private HashSet<String> links = new HashSet<>();
    private int i;

    Crawler() {
    }

    void crawling(String URL, int cr_num, int thread_num, boolean keep_data) throws ClassNotFoundException, SQLException {
        i = cr_num;

        if (!keep_data) {
            parser(URL, thread_num, cr_num);
        } else {
            parser(URL, thread_num, cr_num);
        }


    }

    private void parser(String url, int thread_num, int cr_num) {
        i = cr_num;
        ExecutorService executor = Executors.newFixedThreadPool(thread_num);
        try {
            Document document = (Document) Jsoup.connect(url).get();
            Elements linksOnPage = document.select("a[href]");
            for (Element link : linksOnPage) {
                if (i > 0) {
                    links.add(link.attr("abs:href"));
                    i--;
                }
            }

            for (String x : links) {
                Future<HashSet<String>> sumResult = executor.submit(new Multithreading(x));
                for (String d : sumResult.get()) {
                    if (i > 0) {
                        if (!links.contains(d)) {
                            links.add(d);
                            i--;
                        }
                    } else {
                        executor.shutdown();
                    }
                }
            }
            executor.shutdown();
        } catch (Exception ignored) {
        }
    }


}
