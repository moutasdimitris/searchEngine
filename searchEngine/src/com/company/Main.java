package com.company;


import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws Exception {
        crawler cr=new crawler();
        cr.get_Links("https://www.sammobile.com",10,8,1);
        HashSet<String > links=cr.getHashSet();
        indexer index=new indexer();
        index.clean_html(links);

    }}
