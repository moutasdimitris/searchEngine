package com.company;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.HashSet;


public class Main {

    private static Boolean keepData = false;
    public static int requests = 0;
    public static HashSet<TFD> documentsHashSet = new HashSet<>();

    public static void main(String[] args) throws Exception {
        HashSet<String> words = new HashSet<>();//All words
        HashSet<String> links = new HashSet<>();//All links
        HashSet<HashSet<TFD>> sets;//All (w,d,f)
        Crawler cr = new Crawler();
        getDocuments();
         cr.crawling(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
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
        call.enqueue(new Callback<>() {
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

    public static void getDocuments() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://woodymats.digital:3001/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CallsInterface callsInterface = retrofit.create(CallsInterface.class);
        Call<JsonArray> call = callsInterface.getDocuments();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                int code = response.code();

                if (code == 200) {
                    documentsHashSet = new Gson().fromJson(response.body(), HashSet.class);

                } else {
                    System.out.println("Error with code: " + code);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable throwable) {
            }
        });
    }

}

