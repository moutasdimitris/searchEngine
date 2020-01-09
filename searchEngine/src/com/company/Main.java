package com.company;


import java.util.HashSet;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        double [][] weights;//tf idf weights array
        Scanner sc=new Scanner(System.in);
        System.out.print("Search > ");
        Crawler cr = new Crawler();
        cr.get_Links("https://www.google.com/", 20, 10, true);
        HashSet<String> links = cr.getHashSet();
        Indexer index = new Indexer();
        index.clean_html(links);
        HashSet<HashSet<TFD>> terms=index.getHash();
        String search=sc.nextLine();
        String [] term_to_search=search.split(" ");
        tfdidfcalc tfdidfcalc=new tfdidfcalc();
        for (HashSet<TFD> s : terms) {
            for (TFD tfd:s){
             //System.out.println("("+tfd.getText()+", "+tfd.getDoc_id()+", "+tfd.getFreq()+")");
             }
    }
        int i=0,j=0;
        weights=new double[term_to_search.length][terms.size()];//terms.size()=documents number term_to_search.length=number of terms to search
        for (String term:term_to_search){
        for (HashSet<TFD> str:terms){
                double w=tfdidfcalc.tf_idf_calc(term,str);
                weights[i][j]=w;
                j++;
            }
        j=0;
        i++;
        }
    for (int q=0;q<term_to_search.length;q++){
        for (int w=0;w<terms.size();w++){
        System.out.println(weights[q][w]);
    }}
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Total execution time: "+totalTime/1000000000+"sec.");
    }}

