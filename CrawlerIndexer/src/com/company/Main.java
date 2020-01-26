package com.company;


import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;


public class Main {

    public static Connection conn;
    private static Boolean keepData = false;

    public static void main(String[] args) throws Exception {
        HashSet<String> words = new HashSet<>();//All words
        HashSet<String> links = new HashSet<>();//All links
        HashSet<HashSet<TFD>> sets;//All (w,d,f)
        // create a mysql database connection
//        String myDriver = "com.mysql.jdbc.Driver";
//        String myUrl = "jdbc:mysql://159.203.191.150:3306/SearchEngineDb?useUnicode=yes&characterEncoding=UTF-8";
//        Class.forName(myDriver);
//        conn = DriverManager.getConnection(myUrl, "test", "test");
        Crawler cr = new Crawler();
        // cr.crawling(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
        cr.crawling("https://google.com/", 1, 10, keepData);
        links = cr.getLinks();

        Indexer index = new Indexer();
        index.clean_html(links);
        sets = index.getHash();

        for (HashSet<TFD> s : sets) {
            for (TFD tfd : s) {
                words.add(tfd.getTextTerm());
            }
        }
    }

    public static void sendDocuments(HashSet<TFD> docsHashSet) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://woodymats.digital:3001/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallsInterface callsInterface = retrofit.create(CallsInterface.class);
        Call<JsonObject> call = callsInterface.sendDocuments(docsHashSet, keepData);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                keepData = true;
                if (response.code() == 200) {
                    System.out.println(response.body().get("message").getAsString());
                } else {
                    System.out.println("Error with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                System.out.println("Error with code: onFailure");
            }
        });
    }

}

