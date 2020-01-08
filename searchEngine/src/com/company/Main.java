package com.company;


import java.util.HashSet;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        HashSet<String> allTerms=new HashSet<>();//all terms
        Scanner sc=new Scanner(System.in);
        System.out.print("Search > ");
        String search=sc.next();
        String [] term_to_search=search.split(" ");
        query_processor queryProcessor=new query_processor();
        for (String termToSearch : term_to_search) {
            double tf = queryProcessor.tfCalculator(allTerms, termToSearch);
            // System.out.println("tf is "+tf);
            double idf=queryProcessor.idfCalculator(allTerms,termToSearch);
            // System.out.println(termToSearch+" tf is "+tf+ " and idf is "+idf);
        }

        Crawler cr = new Crawler();
        cr.get_Links("https://www.google.com/", 5, 8, true);
        HashSet<String> links = cr.getHashSet();
        Indexer index = new Indexer();
        index.clean_html(links);
        HashSet<HashSet<TFD>> terms=index.getHash();
        for (HashSet<TFD> s : terms) {
            for (TFD tfd:s){
             System.out.println("("+tfd.getText()+", "+tfd.getDoc_id()+", "+tfd.getFreq()+")");
             }}
    }
}
