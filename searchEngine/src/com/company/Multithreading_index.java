package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Whitelist;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

class TFD{
    String text,doc_id;
    Integer freq;

    String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
    }

    String getDoc_id() {
        return doc_id;
    }

    void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    Integer getFreq() {
        return freq;
    }

    void setFreq(Integer freq) {
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
    public HashSet<TFD> call()  {
        try {
            Document  document = Jsoup.connect(URL).get();
            String v1 = Jsoup.clean(document.html(), Whitelist.none()).toLowerCase();
            Document doc=Jsoup.parse(v1);
            doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
            String [] v=v1.split(" ");
            for (String s : v) {
                if (!s.isEmpty())
                    if (!(s.endsWith(".") && s.endsWith(",") && s.endsWith(";") && s.endsWith("'") && s.endsWith(":"))) {
                       boolean b=check_if_exist(s, sc);
                       if (!b){
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = s.toLowerCase();
                            sc.add(tfd);}
                    } else {
                        if (s.endsWith(".")) {
                            String text = clean_more(s, ".");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text.toLowerCase();
                            sc.add(tfd);
                        }
                        if (s.endsWith(",")) {
                            String text = clean_more(s, ",");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text.toLowerCase();
                            sc.add(tfd);
                        }
                        if (s.endsWith(";")) {
                            String text = clean_more(s, ";");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text.toLowerCase();
                            sc.add(tfd);
                        }
                        if (s.endsWith("'")) {
                            String text = clean_more(s, "'");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text.toLowerCase();
                            sc.add(tfd);
                        }
                        if (s.endsWith(":")) {
                            String text = clean_more(s, ":");
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text.toLowerCase();
                            sc.add(tfd);
                        }
                    }
            }
        } catch (IOException | RejectedExecutionException | IllegalArgumentException e) {
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

    private boolean check_if_exist(String x,HashSet<TFD> h){
        boolean bool=false;
            for (TFD tfd : h) {
                if (tfd.getText().equals(x)){
                    tfd.setFreq(tfd.getFreq()+1);
                    bool=true;
                }
    }
        return bool;
}}
