package com.company;


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

    public static void main(String[] args) throws Exception {
            HashSet<String> links;//All links
            Crawler cr = new Crawler();
            cr.crawling(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
            links = cr.getLinks();
            System.out.println(links);
            Indexer index = new Indexer();
            index.clean_html(links);

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

}

