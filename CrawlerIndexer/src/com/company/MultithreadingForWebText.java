package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Whitelist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

public class MultithreadingForWebText implements Callable<HashSet<TFD>> {
    private String URL;
    private HashSet<TFD> sc = new HashSet<>();
    private String[] exc = {".", ",", ";", "'", ":", "@", "[", "]", "{", "}", "|", "-", "+", "?", "=", "!", "<<", ">>", "&"};
//    PreparedStatement preparedStmt;


    MultithreadingForWebText(String url) {
        this.URL = url;
    }

    @Override
    public HashSet<TFD> call() {
        try {

            // the mysql insert statement
            String query = " insert into records (word,link,freq)" + " values (?, ?, ?)";
            // create the mysql insert preparedstatement
//            preparedStmt = Main.conn.prepareStatement(query);
            Document document = Jsoup.connect(URL).get();
            String v1 = Jsoup.clean(document.html(), Whitelist.none()).toLowerCase();
            Document doc = Jsoup.parse(v1);
            doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
            String[] v = v1.split(" ");
            for (String s : v) {
                if (!s.isEmpty()) {
                    String text = cleaner(s);
                    if (!(text.equals(""))) {
                        boolean b = check_if_exist(text, URL, sc);
                        if (!b) {
                            TFD tfd = new TFD();
                            tfd.documentId = URL;
                            tfd.termFrequency = 1;
                            tfd.textTerm = text.toLowerCase();
                            sc.add(tfd);
                        }
                    }
                }

            }
            Main.sendDocuments(sc);
            System.out.println("Must be " + (++Main.requests) + " and the size of this request is " + sc.size() + " elements.");
//            insertToDb(sc);
        } catch (IOException | RejectedExecutionException | IllegalArgumentException ignored) {
            System.out.println(ignored.getMessage());
        }
        return sc;
    }


    private boolean check_if_exist(String x, String url, HashSet<TFD> h) {
        boolean bool = false;
        for (TFD tfd : h) {
            if (tfd.getTextTerm().equals(x) && tfd.getDocumentId().equals(url)) {
                tfd.setTermFrequency(tfd.getTermFrequency() + 1);
                bool = true;
            }
        }
        return bool;
    }

//    private void insertToDb(HashSet<TFD> d) throws SQLException {
//        for (TFD tfd : d) {
//            preparedStmt.setString(1, tfd.getTextTerm());
//            preparedStmt.setString(2, tfd.getDocumentId());
//            preparedStmt.setInt(3, tfd.getTermFrequency());
//            preparedStmt.execute();
//        }
//        Main.conn.close();
//    }

    private String cleaner(String word) {
        String text = word;
        for (String s : exc) {
            if (text.contains(s)) {
                text = text.replace(s, "");
            }
        }
        return text;
    }

}
