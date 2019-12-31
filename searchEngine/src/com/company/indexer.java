package com.company;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class indexer {
private HashSet<HashSet<TFD>> hash;
    indexer() {
hash=new HashSet<>();
    }


    void clean_html(HashSet<String> urls) throws Exception {
        ExecutorService executor= Executors.newFixedThreadPool(10);
        for (String URL:urls) {
            Future<HashSet<TFD>> sumResult = executor.submit(new Multithreading_index(URL));
            hash.add(sumResult.get());
        }
        executor.shutdown();
        for (HashSet<TFD> s:hash){
        System.out.println(s);}
    }

}
