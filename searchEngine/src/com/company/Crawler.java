package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.*;


class Crawler {
    private HashSet<String> links = new HashSet<>();
    private HashSet<String> hashSet = new HashSet<>();
    private int i;

    Crawler() {
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

    private void parse(String URL, ExecutorService executor) throws ExecutionException, IOException {
        try {
            parsing_URL(URL, executor);
            for (String x : links) {
                Future<HashSet<String>> sumResult = executor.submit(new Multithreading(x));
                for (String d : sumResult.get()) {
                    if (i > 0) {
                        hashSet.add(d);
                        i--;
                    } else {
                        executor.shutdown();
                    }
                }
            }
        } catch (InterruptedException | RejectedExecutionException e) {
            System.out.println("Task exception: " + e.getMessage());
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }


    private void parsing_URL(String URL, ExecutorService executor) {
        try {
            Document document = Jsoup.connect(URL).get();
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
        } catch (IOException | RejectedExecutionException e) {
            System.out.println("Task exception: " + e.getMessage());
        }
    }

    HashSet<String> getHashSet() {
        return hashSet;
    }
}
