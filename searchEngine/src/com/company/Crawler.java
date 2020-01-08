package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.*;


class Crawler {
    private HashSet<String> links ;
    private HashSet<String> hashSet;
    private int i;

    Crawler() {
        links = new HashSet<>();
        hashSet = new HashSet<>();
    }


    //cr_num einai to plithos ton selidon pou thelume na kanoume crawling
    void get_Links(String URL, int cr_num, int thread_num, boolean keep_data) throws Exception {
        //Creating threads
        i = cr_num;
        ExecutorService executor = Executors.newFixedThreadPool(thread_num);
        if (!keep_data) {
            links.clear();
            parse(URL, executor);
        } else {
            parse(URL, executor);
        }
    }

    private void parse(String URL, ExecutorService executor) throws ExecutionException {
        try {
            parsing_URL(URL, executor);
            for (String x : links) {
                Multithreading mul=new Multithreading(x);
                Future<HashSet<String>> sumResult = executor.submit(mul);
                for (String d : sumResult.get()) {
                    if (i > 0) {
                        hashSet.add(d);
                        i--;
                    } else {
                        executor.shutdown();
                    }
                }
            }
        } catch (InterruptedException | RejectedExecutionException | IllegalArgumentException ignored) {
        }
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
    }


    private void parsing_URL(String URL, ExecutorService executor) {
        try {
            URL url1=new URL(URL);
            Document document = Jsoup.connect(String.valueOf(url1)).get();
            Elements linksOnPage = document.select("a[href]");
            for (Element link : linksOnPage) {
                links.add(link.attr("abs:href"));
                if (i > 0) {
                    hashSet.add(link.attr("abs:href"));
                    i--;
                } else {
                    executor.shutdown();
                }
            }
        } catch (IOException | RejectedExecutionException ignored) {

        }
    }

    HashSet<String> getHashSet() {
        return hashSet;
    }
}
