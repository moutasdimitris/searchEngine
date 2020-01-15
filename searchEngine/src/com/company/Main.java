package com.company;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception{
        long startTime = System.nanoTime();
        HashSet<String> words=new HashSet<>();//All words
        HashSet<String> links;//All links
        HashSet<HashSet<TFD>> sets;//All (w,d,f)

        Crawler cr = new Crawler();
        cr.crawling("https://www.sammobile.com/", 50, 10, true);
        links=cr.getLinks();

        Indexer index = new Indexer();
        index.clean_html(links);
        sets=index.getHash();

        for (HashSet<TFD> s : sets) {
            for (TFD tfd:s){
                words.add(tfd.getText());
             }
        }
        Scanner sc=new Scanner(System.in);
        String search;

        do {
            System.out.print("Search > ");
            search=sc.nextLine();
            QueryProcessor queryProcessor = new QueryProcessor(search);
            double[] results = queryProcessor.CalculateResults(words, links.size(), sets);
            System.out.println("Final cosine similarity ");
            System.out.println(Arrays.toString(results));
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("Total execution time: "+totalTime/1000000000+"sec.");
        }while (!search.equals(""));

    }}

