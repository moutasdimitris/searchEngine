package com.company;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class Indexer {
    private HashSet<HashSet<TFD>> tfd;

     HashSet<HashSet<TFD>> getHash() {
        return tfd;
    }
    Indexer() {
        tfd = new HashSet<>();
    }


    void clean_html(HashSet<String> urls) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String URL : urls) {
            Future<HashSet<TFD>> sumResult = executor.submit(new MultithreadingForWebText(URL));
            tfd.add(sumResult.get());
        }
        executor.shutdown();
    }

}
