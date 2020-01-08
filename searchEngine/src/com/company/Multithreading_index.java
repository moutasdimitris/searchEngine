package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;


public class Multithreading_index implements Callable<HashSet<TFD>> {
        private String URL;
        private HashSet<TFD> sc=new HashSet<>();
        private String [] exc={".",",",";","'",":","@","[","]","{","}","|","-","+","?","=",};


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
                if (!s.isEmpty()){
                    String text=cleaner(s);
                    if (!(text.equals(""))){
                       boolean b=check_if_exist(text, sc);
                       if (!b){
                            TFD tfd = new TFD();
                            tfd.doc_id = URL;
                            tfd.freq = 1;
                            tfd.text = text.toLowerCase();
                            sc.add(tfd);}}
                    }
            }
        } catch (IOException | RejectedExecutionException | IllegalArgumentException ignored) {

        }
        return sc;
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
