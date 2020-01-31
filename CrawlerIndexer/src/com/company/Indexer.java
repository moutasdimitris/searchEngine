package com.company;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Η κλάση αυτή υλοποιεί τις βασικές μεθόδους που θα χρειαστούν για τον
 * Indexer.
 * @author Matskidis Ioannis
 * @author Moutafidis Dimitrios
 */
class Indexer {

    /**
     * Constructor.
     */
    Indexer() {
    }

    /**
     * Μέθοδος η οποία καλεί την MultithreadingForWebText και φροντίζει για τη δημιουργία του καταλόγου
     * κάνοντας παράλληλα όλες τις απαραίτητες αλλαγές (καθαρισμός λέξης,έλεγχος εάν υπάρχει ήδη) και τις επιστρέφει.
     * @param urls HASHSET<STRING> urls.
     * @throws Exception Για την Get().
     */
    void cleanHtml(HashSet<String> urls) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String URL : urls) {
            Future<HashSet<TFD>> sumResult = executor.submit(new MultithreadingForWebText(URL));
            sumResult.get();
        }
        executor.shutdown();
    }
}
