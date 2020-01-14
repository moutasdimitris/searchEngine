package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

public class Multithreading implements Callable <HashSet<String>> {
    private  String url;
    private HashSet<TFD> sc=new HashSet<>();
    private String [] exc={".",",",";","'",":","@","[","]","{","}","|","-","+","?","=",};


    private  HashSet<String> link=new HashSet<>();


    Multithreading(String url) {
        this.url=url;
    }

    public HashSet<TFD> getSc() {
        return sc;
    }

    @Override
    public HashSet<String> call() {
        try {
            URL url1=new URL(url);
            Document document = Jsoup.connect(String.valueOf(url1)).get();
            String text=Jsoup.clean(document.html(), Whitelist.none()).toLowerCase();
            Document doc=Jsoup.parse(text);
            doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
            String [] v=text.split(" ");
            for (String s : v) {
                if (!s.isEmpty()){
                    String word=cleaner(s);
                    if (!(text.equals(""))){
                        boolean b=check_if_exist(text, sc);
                        if (!b){
                            TFD tfd = new TFD();
                            tfd.doc_id = url;
                            tfd.freq = 1;
                            tfd.text = word.toLowerCase();
                            sc.add(tfd);}}
                }
            }
            Elements linksOnPage = document.select("a[href]");
            for (Element links : linksOnPage) {
                link.add(links.attr("abs:href"));
            }

        }catch (IOException | RejectedExecutionException | IllegalArgumentException ignored){
        }
        return link;
    }


    private boolean check_if_exist(String x,HashSet<TFD> h){
        boolean bool=false;
        for (TFD tfd : h) {
            if (tfd.getText().equals(x)){
                tfd.setFreq(tfd.getFreq()+1);
                bool=true;
            }
        }
        return bool;
    }

    private String cleaner(String word){
        String text=word;
        for (String s : exc) {
            if (text.contains(s)) {
                text = text.replace(s, "");
            }
        }
        return text;
    }
}
