package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashSet;
import java.util.concurrent.*;

/**
 * Η ΚΛΑΣΗ ΑΥΤΗ ΥΛΟΠΟΙΕΙ ΤΙΣ ΒΑΣΙΚΕΣ ΜΕΘΟΔΟΥΣ ΓΙΑ ΤΗΝ ΚΑΤΑΣΚΕΥΗ ΤΟΥ CRAWLER.
 *
 */
public class Crawler {

    /**
     * ΕΠΙΣΤΡΕΦΕΙ ΤΟ HASHSET ΜΕ ΤΑ LINK ΤΑ ΟΠΟΙΑ ΘΑ ΚΑΝΟΥΜΕ CRAWL ΓΙΑ
     * ΝΑ ΠΑΡΟΥΜΕ ΤΟ ΚΕΙΜΕΝΟ ΠΟΥ ΠΕΡΙΕΧΟΥΝ.
     */
    public HashSet<String> getLinks() {
        return links;
    }

    private HashSet<String> links = new HashSet<>();
    private int i;

    Crawler() {
    }

    /**
     * Η ΜΕΘΟΔΟΣ ΑΥΤΗ ΚΑΛΕΙ ΤΗ ΜΕΘΟΔΟ PARSER ΚΑΙ ΣΤΗΝ ΟΠΟΙΑ
     * ΔΙΝΕΙ ΤΑ ΟΡΙΣΜΑΤΑ URL,THREADNUM KAI CRNUM.
     * ΕΠΙΣΗΣ ?????????
     * @param URL ΑΡΧΙΚΟ URL ΑΠΟ ΤΟ ΟΠΟΙΟ ΘΑ ΞΕΚΙΝΗΣΕΙ ΤΟ CRAWLING.
     * @param crNum ΤΟ ΠΛΗΘΟΣ ΤΩΝ ΣΕΛΙΔΩΝ ΠΟΥ ΘΑ ΚΑΝΕΙ CRAWLING.
     * @param threadNum ΤΟ ΠΛΗΘΟΣ ΤΩΝ THREAD.
     * @param keepData ΕΠΙΛΟΓΗ ΕΑΝ ΘΑ ΚΡΑΤΗΘΟΥΝ ΤΑ ΠΡΟΗΓΟΥΜΕΝΑ ΔΕΔΟΜΕΝΑ.
     */
    void crawling(String URL, int crNum, int threadNum, boolean keepData)  {
            i = crNum;
            parser(URL, threadNum, crNum);
    }


    /**
     * Η ΜΕΘΟΔΟΣ ΑΥΤΗ ΔΕΧΕΤΑΙ ΤΑ ΠΑΡΑΚΑΤΩ ΟΡΙΣΜΑΤΑ ΚΑΙ ΥΠΟΛΟΓΙΖΕΙ ΤΑ
     * LINK ΤΑ ΟΠΟΙΑ ΠΡΕΠΕΙ ΝΑ ΠΑΡΕΙ ΤΟ ΚΕΙΜΕΝΟ ΚΑΙ ΝΑ ΔΩΣΕΙ ΣΤΟΝ INDEXER.
     * @param url ΑΡΧΙΚΟ URL ΑΠΟ ΤΟ ΟΠΟΙΟ ΘΑ ΞΕΚΙΝΗΣΕΙ ΤΟ CRAWLING.
     * @param threadΝum ΤΟ ΠΛΗΘΟΣ ΤΩΝ THREAD.
     * @param crNum ΤΟ ΠΛΗΘΟΣ ΤΩΝ ΣΕΛΙΔΩΝ ΠΟΥ ΘΑ ΚΑΝΕΙ CRAWLING.
     */
    private void parser(String url, int threadΝum, int crNum) {
        i = crNum;
        ExecutorService executor = Executors.newFixedThreadPool(threadΝum);
        try {
            Document document = (Document) Jsoup.connect(url).get();
            Elements linksOnPage = document.select("a[href]");
            for (Element link : linksOnPage) {
                if (i > 0) {
                    links.add(link.attr("abs:href"));
                    i--;
                }
            }

            for (String x : links) {
                Future<HashSet<String>> sumResult = executor.submit(new Multithreading(x));
                for (String d : sumResult.get()) {
                    if (i > 0) {
                        if (!links.contains(d)) {
                            links.add(d);
                            i--;
                        }
                    } else {
                        executor.shutdown();
                    }
                }
            }
            executor.shutdown();
        } catch (Exception ignored) {
        }
    }


}
