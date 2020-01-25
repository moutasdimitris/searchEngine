package com.company;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception{
        HashSet<String> words=new HashSet<>();//All words
        HashSet<String> links;//All links
        HashSet<HashSet<TFD>> sets;//All (w,d,f)
        Crawler cr = new Crawler();
       // cr.crawling(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
        cr.crawling("https://www.theverge.com",1,10,false);
        links=cr.getLinks();

        Indexer index = new Indexer();
        index.clean_html(links);
        sets=index.getHash();

        for (HashSet<TFD> s : sets) {
            for (TFD tfd:s){
                words.add(tfd.getText());
             }
        }
    }
}

