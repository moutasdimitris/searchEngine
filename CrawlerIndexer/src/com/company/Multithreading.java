package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

/**
 * Η κλάση αυτή παίρνει μία σελίδα και από αυτήν βλέπει ποιές σελίδες υπάρχουν
 * μέσα σε αυτήν και τις επιστρέφει.
 * @author Matskidis Ioannis
 * @author Moutafidis Dimitrios
 */
public class Multithreading implements Callable <HashSet<String>> {
    private  String url;
    private  HashSet<String> link=new HashSet<>();

    /**
     * Constructor που δέχεται ένα URL και το εισάγει στο String URL.
     * @param url To Url εισόδου.
     */
    Multithreading(String url) {
        this.url=url;
    }

    /**
     * Η μέθοδος call διαβάζει τον κώδικα HTML της σελίδας και βρίσκει τυχόν λινκ σε άλλες
     * σελίδες που μπορεί να περιέχονται μέσα στο αρχικό λινκ και τις συλλέγει επιστρέφοντας
     * σε ένα HashSet.
     * @return HashSet με τα λινκ που περιέχονται μέσα στη σελίδα URL.
     */
    @Override
    public HashSet<String> call() {
        try {
            URL url1=new URL(url);
            Document document = Jsoup.connect(String.valueOf(url1)).get();
            Elements linksOnPage = document.select("a[href]");
            for (Element links : linksOnPage) {
                link.add(links.attr("abs:href"));
            }
        }catch (IOException | RejectedExecutionException | IllegalArgumentException ignored){
        }
        return link;
    }
}


