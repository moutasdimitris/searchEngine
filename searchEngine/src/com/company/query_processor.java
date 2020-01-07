package com.company;

import java.util.HashSet;


class query_processor {


    double tfCalculator(HashSet<String> totalterms, String termToCheck)
    {
        double count = 0;
        for (String s : totalterms)
        {
            if (s.equals(termToCheck))
            {
                count++;
            }
        }
        //System.out.println("counter is "+ count+ " "+totalterms.size());
        double d=count/(double)totalterms.size();
        System.out.println("d is "+d);
        return d;
    }


   double idfCalculator(HashSet<String> totalTerms, String termToCheck) {
        double count = 0;
        for (String s : totalTerms) {
            if (s.equals(termToCheck)) {
                    count++;
                    break;
                }
            }
            return 1 + Math.log(totalTerms.size() / count);

    }}
