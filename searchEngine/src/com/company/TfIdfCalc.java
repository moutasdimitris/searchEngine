package com.company;

import java.util.HashSet;


class TfIdfCalc {


     double tfCalculator(String str, HashSet<TFD> links)
    {
        double maxl=0;
        double freq=0;
        for (TFD tfd:links)
        {
            maxl+=tfd.getFreq();
            if (str.equals(tfd.getText()))
            {
                freq++;
            }
        }
        if (freq==0){
            return 0.0;
        }
        return (freq/maxl);
    }


    double idfCalculator(String str, HashSet<TFD> links) {
    double count=0;
        for (TFD tfd:links){
            if (tfd.getText().equals(str)){
                count++;
            }
        }
        if (count==0){
            return 0.0;
        }
            return  Math.log(links.size()/count);

    }


}
