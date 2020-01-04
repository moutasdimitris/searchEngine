package com.company;


import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws Exception {
        Crawler cr = new Crawler();
        cr.get_Links("https://www.google.com/", 10, 8, true);
        HashSet<String> links = cr.getHashSet();
        Indexer index = new Indexer();
        index.clean_html(links);

    }
}
