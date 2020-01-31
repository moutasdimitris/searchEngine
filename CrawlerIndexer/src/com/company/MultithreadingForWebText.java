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

/**
 * Κλάση η οποία υλοποιεί τις βασικές διαδικασίες για την δημιουργία του καταλόγου.
 * @author Matskidis Ioannis
 * @author Moutafidis Dimitrios
 */
public class MultithreadingForWebText implements Callable<HashSet<TFD>> {
    private String URL;
    private HashSet<TFD> sc = new HashSet<>();
    private String[] exc = {".", ",", ";", "'", ":", "@", "[", "]", "{", "}", "|", "-", "+", "?", "=", "!", "<<", ">>", "&"};

    /**
     * Constructor που δέχεται το Url και το αναθέτει στο String Url.
     * @param url URL.
     */
    MultithreadingForWebText(String url) {
        this.URL = url;
    }

    /**
     * Μέθοδος η οποία συνδέεται στο Url παίρνει το κείμενο το καθαρίζει απο τα στοιχεία
     * που περιέχει ο πίνακας exc και στη συνέχεια βάζει την "καθαρή" λέξη στη συλλογή.
     * @return Επιστρέφει τη συλλογή με τα στοιχεία (T,D,F).
     */
    @Override
    public HashSet<TFD> call() {
        try {
            System.out.println("url is "+URL);
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
        } catch (IOException | RejectedExecutionException | IllegalArgumentException ignored) {
            System.out.println(ignored.getMessage());
        }
        return sc;
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν υπάρχει μέσα στο σύνολο των δεδομένων η
     * λέξη χ και αντίστοιχα επιστρέφει true or false. Επίσης σε περίπτωση που βρεθεί
     * η λέξη μέσα στο σύνολο τότε αυξάνει τη συχνότητα εμφάνισής της κατα 1.
     * @param x Η λέξη που θέλουμε να βρούμε εάν υπάρχει.
     * @param url Url της λέξης χ για τον έλεγχο γιατί σε περίπτωση που υπάρχει ήδη η
     *            λέξη αλλά με διαφορετικό url δεν εισάγεται πράγμα μη επιθυμητό.
     * @param h Το σύνολο των δεδομένων στο οποίο θα ψάξουμε για τη λέξη χ.
     * @return True or false ανάλογα με το εάν βρέθηκε η λέξη ή όχι.
     */
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

    /**
     * Η μέθοδος αυτή παίρνει μία λέξη και την καθαρίζει από όλα τα σύμβολα
     * τα οποία περιέχονται στον πίνακα sc και την οποία επιστρέφει.
     * @param word Η λέξη που θέλουμε να καθαρίσουμε.
     * @return Η καθαρή λέξη.
     */
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
