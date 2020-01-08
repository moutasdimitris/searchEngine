package com.company;

import java.util.HashSet;


class query_processor {

double tf_idf_calc(String str, HashSet<TFD> links){
    double d1=tfCalculator(str,links);
    double d2=idfCalculator(str,links);
    return d1/d2;
}

    private double tfCalculator(String str, HashSet<TFD> links)
    {
        double maxl=0;
        double freq=0;
        for (TFD tfd:links)
        {
            maxl+=tfd.getFreq();
            if (str.equals(tfd.getText()))
            {
                freq=tfd.getFreq();
            }
        }
        return freq/maxl;
    }


   private double idfCalculator(String str, HashSet<TFD> links) {
    double count=0;
        for (TFD tfd:links){
            if (tfd.getText().equals(str)){
                count++;
            }

        }
            return  Math.log(links.size() / count);

    }}
