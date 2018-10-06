package com.example.pc.myotd.Model;

/**
 * Created by pierpawel on 27/12/2015.
 */
public class IttenAlgorithm {

    Integer[] colori;

    public IttenAlgorithm(Integer[] colori){
       this.colori=colori;

    }

    public boolean Abbinamento(){
        int sum=0;
        int nColoriUguali=0;
        int coloriUguali=0;
        int temp=0;
        if(colori.length>0)
        for(int i=0; i<colori.length; i++){
            sum=sum+colori[i];
            if(temp!=nColoriUguali && i>0) {
                coloriUguali = coloriUguali + colori[i - 1];
                temp=nColoriUguali;
            }
            for(int j=i+1; j<colori.length-i-1; j++){
                    if(colori[i]==colori[j]){
                        nColoriUguali++;
                    }
                }
            temp++;
            nColoriUguali++;
        }


        if(nColoriUguali%2==0)
        sum=sum-coloriUguali;
        if(sum==0)
            return true;


         return false;
    }
}
