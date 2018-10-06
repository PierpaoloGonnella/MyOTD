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
        int sum2=0;
        for(int i=0; i<colori.length; i++){
            sum=sum+colori[i];
                for(int j=i+1; j<colori.length-i-1; j++){
                    if(colori[i]==colori[j])
                    sum2=sum2+j;
                }
        }

        sum=sum-sum2;
        if(sum==0)
            return true;


         return false;
    }
}
