package com.company;

/**
 * Κλάση που υλοποιεί τα πεδία μιας εγγραφής στη συλλογή των στοιχείων μαζί με
 * Setters και Getters για την προσπέλαση και εισαγωγή στοιχείων.
 * @author Matskidis Ioannis
 * @author Moutafidis Dimitrios
 */
 class TFD {
        String textTerm;
        String documentId;
        Integer termFrequency;

        String getTextTerm() {
            return textTerm;
        }

        String getDocumentId() {
            return documentId;
        }

        Integer getTermFrequency() {
            return termFrequency;
        }

        void setTermFrequency(Integer termFrequency) {
            this.termFrequency = termFrequency;
        }


}
