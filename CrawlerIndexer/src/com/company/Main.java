package com.company;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class Main {

    public static Connection conn;

    public static void main(String[] args) throws Exception {
        HashSet<String> words = new HashSet<>();//All words
        HashSet<String> links;//All links
        HashSet<HashSet<TFD>> sets;//All (w,d,f)
        // create a mysql database connection
        String myDriver = "com.mysql.jdbc.Driver";
        String myUrl = "jdbc:mysql://159.203.191.150:3306/SearchEngineDb?useUnicode=yes&characterEncoding=UTF-8";
        Class.forName(myDriver);
        conn = DriverManager.getConnection(myUrl, "test", "test");
        Crawler cr = new Crawler();
        // cr.crawling(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
        cr.crawling("https://techblog.gr/", 3, 10, false);
        links = cr.getLinks();

        Indexer index = new Indexer();
        index.clean_html(links);
        sets = index.getHash();

        for (HashSet<TFD> s : sets) {
            for (TFD tfd : s) {
                words.add(tfd.getText());
            }
        }
    }
}

