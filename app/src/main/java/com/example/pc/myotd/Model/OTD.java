package com.example.pc.myotd.Model;

import com.example.pc.myotd.data.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by PIER on 12/01/2016.
 */
public class OTD {

    private List<Capo> lista;
    private List<Testa> testa;
    private List<Busto> busto;
    private List<Gambe> gambe;
    private List<Piedi> piedi;

    public OTD(List<Capo> lista){
        this.lista=lista;
    }

    public boolean colorControl(){

        Integer[] colors=new Integer[lista.size()];
        int count=0;
        int e=0;
        for(Capo c: lista){
            colors[count]=c.getColor();
            count++;
        }

        for(int i=0; i<count; i++){

            if(colors[i]!=0)
            for(int j=i+1;j<count-i-1;j++)
            {

                if(colors[i]==colors[j])
                    e++;
            }
        }

        if(e>=count)
            return false;
        else
            return true;


    }

    private void  differenzia(){
         testa=new ArrayList<>();
      busto=new ArrayList<>();
       gambe=new ArrayList<>();
        piedi=new ArrayList<>();

   for(Capo c: lista){

            if(c.getCategoria()==Categoria.TESTA)
                testa.add((Testa)c);
            if(c.getCategoria()==Categoria.BUSTO)
                busto.add((Busto)c);
            if(c.getCategoria()==Categoria.GAMBE)
                gambe.add((Gambe)c);
            if(c.getCategoria()==Categoria.PEDI)
                piedi.add((Piedi)c);
        }
    }


    private List<Capo>  filtraPerTipo(String tipo){

        List<Capo> result= new ArrayList<>();
        for(Capo c: lista){
           if(c.getTipo().equalsIgnoreCase(tipo))
            result.add(c);}
        return result;
    }

    public List<Capo> creaAbbinamentoColori(){

        differenzia();
        List<Capo> otd=new ArrayList<>();
        Integer[] colori= new Integer[4];
        for(int i=0; i<4; i++){
            colori[i]=-1;
        }

       if(lista.size()>4 && colorControl())
        while( new IttenAlgorithm(colori).Abbinamento()==false) {
            Random random =new Random();

            int a,b,c,d;

            int i = random.nextInt(testa.size());
            a= testa.get(i).getColor();
            colori[0]=a;
            otd.add((Capo)testa.get(i));

              i = random.nextInt(busto.size());
               b= busto.get(i).getColor();
                colori[1]=b;
                otd.add((Capo)busto.get(i));


            i = random.nextInt(gambe.size());
               c= gambe.get(i).getColor();
                colori[2]=c;
                otd.add((Capo)gambe.get(i));


            i = random.nextInt(piedi.size());
               d= piedi.get(i).getColor();
                colori[3]=d;
                otd.add((Capo)piedi.get(i));

          if(new IttenAlgorithm(colori).Abbinamento()==false)
             otd.clear();

        }
        else {
           otd.add((Capo)testa.get(0));
           otd.add((Capo)busto.get(0));
           otd.add((Capo)gambe.get(0));
           otd.add((Capo)piedi.get(0));
       }
        return otd;
    }

    public List<Capo> creaAbbinamento(List<Capo> list){

        this.lista=list;
        differenzia();
        List<Capo> otd=new ArrayList<>();
        List<Capo> temp=new ArrayList<>();
        Random random =new Random();


        int i = random.nextInt(testa.size());
        otd.add(testa.get(i));
        testa.remove(i);

        temp=filtraPerTipo("cappotto");
        i = random.nextInt(temp.size());

        otd.add(temp.get(i));

        List<Capo> tshirt=filtraPerTipo("T-shirt");
        List<Capo> maglia=filtraPerTipo("maglia");
        List<Capo> polo=filtraPerTipo("polo");

        temp=new ArrayList<>();
        if(!tshirt.isEmpty()&&tshirt!=null)
        for(int j=0;j<tshirt.size();j++){
         temp.add(tshirt.get(j)) ;
        }
        if(!maglia.isEmpty()&&maglia!=null)
            for(int j=0;j<maglia.size();j++){
                temp.add(maglia.get(j)) ;
            }
        if(!polo.isEmpty()&&polo!=null)
            for(int j=0;j<polo.size();j++){
                temp.add(polo.get(j)) ;
            }
        i = random.nextInt(temp.size());
        otd.add(temp.get(i));

        temp=new ArrayList<>();
        List<Capo> felpa=filtraPerTipo("felpa");
        List<Capo> maglione=filtraPerTipo("maglione");

        temp=new ArrayList<>();
        if(!felpa.isEmpty()&&felpa!=null)
            for(int j=0;j<felpa.size();j++){
                temp.add(felpa.get(j)) ;
            }
        if(!maglione.isEmpty()&&maglione!=null)
            for(int j=0;j<maglione.size();j++){
                temp.add(maglione.get(j)) ;
            }

        i = random.nextInt(temp.size());
        otd.add(temp.get(i));

        i = random.nextInt(gambe.size());
        otd.add(gambe.get(i));
        gambe.remove(i);
        i = random.nextInt(piedi.size());
        otd.add(piedi.get(i));
        piedi.remove(i);
        return otd;
    }

    public List<Capo> creaAbbinamentoMeteo(){

        differenzia();
        List<Capo> otd;
        int max= YahooWeatherService.getTMax();
        int min= YahooWeatherService.getTMin();
        int temperature= YahooWeatherService.getT();

        int escursioneTermica= max-min;

        otd=filtraPerCondizioniMeteo(getConditionFromRange(temperature), filtraPerStagione(getSeason(getCurrentMonth())));
        //mi sono fermato qua 28 gennaio 2016
        //if(escursioneTermica>=5)
        otd=creaAbbinamento(otd);

        return otd;
    }

    public void controlloMeteo(){
  // da implementare forse
    }

    String getSeason(int month) {
        switch(month) {
            case 11:
            case 12:
            case 1:
            case 2:
                return "Inverno";
            case 3:
            case 4:
                return "Primavera";
            case 5:
            case 6:
            case 7:
            case 8:
                return "Estate";
            default:
                return "Autunno";
        }
    }

    public int getCurrentMonth(){
       return Calendar.getInstance().get(Calendar.MONTH)+1;
    }


    public List<Capo> filtraPerCondizioniMeteo(String condizione, List<Capo> lista){

        this.lista=lista;
        differenzia();
        List<Capo> result=new ArrayList<>();
        for(int i=0; i<testa.size(); i++){
            if(testa.get(i).getCondizioniAsString().contains(condizione))
                result.add(testa.get(i));
        }
        for(int i=0; i<busto.size(); i++){
            if(busto.get(i).getCondizioniAsString().contains(condizione))
                result.add(busto.get(i));
        }
        for(int i=0; i<gambe.size(); i++){
            if(gambe.get(i).getCondizioniAsString().contains(condizione))
                result.add(gambe.get(i));
        }
        for(int i=0; i<piedi.size(); i++){
            if(piedi.get(i).getCondizioniAsString().contains(condizione))
                result.add(piedi.get(i));
        }
       return result;
    }

    public List<Capo> filtraPerOccasioni(String occasione, List<Capo> lista){

        this.lista=lista;
        differenzia();
        List<Capo> result=new ArrayList<>();
        for(int i=0; i<testa.size(); i++){
            if(testa.get(i).getOccasioniAsString().contains(occasione))
                result.add(testa.get(i));
        }
        for(int i=0; i<busto.size(); i++){
            if(busto.get(i).getOccasioniAsString().contains(occasione))
                result.add(busto.get(i));
        }
        for(int i=0; i<gambe.size(); i++){
            if(gambe.get(i).getOccasioniAsString().contains(occasione))
                result.add(gambe.get(i));
        }
        for(int i=0; i<piedi.size(); i++){
            if(piedi.get(i).getOccasioniAsString().contains(occasione))
                result.add(piedi.get(i));
        }
        return result;
    }



    public List<Capo> filtraPerStagione(String stagione){

        differenzia();
        List<Capo> result=new ArrayList<>();
        for(int i=0; i<testa.size(); i++){
            if(testa.get(i).getStagioniAsString().contains(stagione))
                result.add(testa.get(i));
        }
        for(int i=0; i<busto.size(); i++){
            if(busto.get(i).getStagioniAsString().contains(stagione))
                result.add(busto.get(i));
        }
        for(int i=0; i<gambe.size(); i++){
            if(gambe.get(i).getStagioniAsString().contains(stagione))
                result.add(gambe.get(i));
        }
        for(int i=0; i<piedi.size(); i++){
            if(piedi.get(i).getStagioniAsString().contains(stagione))
                result.add(piedi.get(i));
        }
        return result;
    }



   

    public String getConditionFromRange(int temp){
        if(temp<=5)
            return "Neve";
        if(temp>=20)
            return "Sole";
        return null;
    }

}
