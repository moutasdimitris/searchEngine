package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

class TFD{
    String text,doc_id;
    Integer freq;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public Integer getFreq() {
        return freq;
    }

    public void setFreq(Integer freq) {
        this.freq = freq;
    }

}

public class Multithreading_index implements Callable<HashSet<TFD>> {
        private String URL;
        private HashSet<TFD> sc=new HashSet<>();


    Multithreading_index(String url) {
        this.URL=url;
    }

    @Override
    public HashSet<TFD> call() {
        try {
            Document  document = Jsoup.connect(URL).get();
            String[] v = Jsoup.clean(document.html(), Whitelist.none()).split(" ",0);
            for (String s : v) {
                if (!s.isEmpty()) {
                    if (!(s.endsWith(".") && s.endsWith(",") && s.endsWith(";")  && s.endsWith("'") && s.endsWith(":"))){
                        TFD tfd = new TFD();
                        tfd.doc_id = URL;
                        tfd.freq = 1;
                        tfd.text = s;
                        sc.add(tfd);
                }else{
                        if (s.endsWith(".")){
                            String text=clean_more(s,".");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text;
                            sc.add(tfd);}
                        if  (s.endsWith(",")) {
                            String text=clean_more(s,",");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text;
                            sc.add(tfd); }
                        if  (s.endsWith(";")) {
                            String text=clean_more(s,";");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text;
                            sc.add(tfd);}
                        if  (s.endsWith("'")){
                            String text=clean_more(s,"'");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text;
                            sc.add(tfd);}
                        if  (s.endsWith(":")){
                            String text=clean_more(s,":");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text;
                            sc.add(tfd);}
                }}
            }
        } catch (IOException | RejectedExecutionException e) {
            System.out.println("Something went wrong: "+e.getMessage());
        }
        return sc;
    }

    private String clean_more(String text,String symbol){
        String s="";
            if (text.endsWith(symbol))
                s=text.replace(symbol,"");
        return s;
    }
}
