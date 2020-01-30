package com.company;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ΚΛΑΣΗ ΠΟΥ ΥΛΟΠΟΙΕΙ ΤΙΣ ΒΑΣΙΚΕΣ ΜΕΘΟΔΟΥΣ ΓΙΑ ΤΟΝ INDEXER.
 */
class Indexer {

    /**
     * CONSTRUCTOR ΠΟΥ ΜΕ ΤΟ ΚΑΛΕΣΜΑ ΤΟΥ ΔΗΜΙΟΥΡΓΕΙΤΑΙ ΤΟ HASHSET ΟΠΟΥ ΘΑ ΜΠΟΥΝΕ ΟΙ ΕΓΓΡΑΦΕΣ.
     */
    Indexer() {
    }

    /**
     * ΜΕΘΟΔΟΣ ΜΕ ΤΗΝ ΟΠΟΙΑ ΠΑΙΡΝΕΙ ΤΟ ΚΕΙΜΕΝΟ ΑΠΟ ΤΙΣ ΣΕΛΙΔΕΣ ΠΟΥ ΜΑΣ ΕΔΩΣΕ Ο CRAWLER.
     * @param urls HASHSET<STRING> URLS
     * @throws Exception ΓΙΑ ΤΗ GET()
     */
    void clean_html(HashSet<String> urls) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String URL : urls) {
            Future<HashSet<TFD>> sumResult = executor.submit(new MultithreadingForWebText(URL));
            sumResult.get();
            //tfd.add(sumResult.get());
        }
        executor.shutdown();
    }
}
