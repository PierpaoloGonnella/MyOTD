package com.example.pc.myotd.Model;

/**
 * Created by pierpawel on 27/12/2015.
 */
public class Capo {

    private Categoria categoria;
    private IttenColor color1;
    private IttenColor color2;
    private String marca;
    private int[] stagioni=new int[4];
    private  int[] condizioni=new int[4];
    private  int[] occasioni=new int[3];
    private int index;
    private String tipo;
    private int id;

    // L'array stagione puo contenere a seconda del valore(0-1) rispettivamente
    // 0.Inverno
    // 1.Autunno
    // 2.Primavera
    // 3.Estate

    public Capo(int id,Categoria categoria,IttenColor color1, IttenColor color2, String marca, int[] stagioni, int[] condizioni,int[] occasioni, String tipo){

        this.id=id;
        this.stagioni=stagioni;
        this.categoria=categoria;
        this.color1=color1;
        this.color2=color2;
        this.marca=marca;
        this.condizioni=condizioni;
        this.occasioni=occasioni;
        this.tipo=tipo;
    }



    public int getId(){return this.id;}

    public void setId(int i){this.id=i;}

    public void setTipo(String t){this.tipo=t;}

    public String getTipo(){return this.tipo;}

    public Categoria getCategoria(){
        return this.categoria;
    }

    public void setCategoria(String s){
        if(s.equalsIgnoreCase("Testa"))
            this.categoria= Categoria.TESTA;
        if(s.equalsIgnoreCase("Busto"))
            this.categoria= Categoria.BUSTO;
        if(s.equalsIgnoreCase("Gambe"))
            this.categoria= Categoria.GAMBE;
        if(s.equalsIgnoreCase("Piedi"))
            this.categoria= Categoria.PEDI;
    }

    public int getColor(){
        return this.color1.numCode();
    }

    public String getMarca(){
        return this.marca;
    }

    public void setCat(Categoria cat){
        this.categoria= cat;
    }

    public void setColor1(IttenColor color) {
        this.color1 = color;
    }

    public void setColor2(IttenColor color) {
        this.color2 = color;
    }


    public void setColor1AsString(String s){
        if(s.equals("Jeans"))
            this.color1= IttenColor.Jeans;
        if(s.equals("Grigio"))
            this.color1= IttenColor.Grigio;
        if(s.equals("Marrone"))
            this.color1= IttenColor.Marrone;
        if(s.equals("BluNavy"))
            this.color1= IttenColor.BluNavy;
        if(s.equals("Nero"))
            this.color1= IttenColor.Nero;
        if(s.equals("Bianco"))
            this.color1= IttenColor.Bianco;
        if(s.equals("Blu"))
            this.color1= IttenColor.Blu;
        if(s.equals("Arancio"))
            this.color1= IttenColor.Arancio;
        if(s.equals("BluViola"))
            this.color1= IttenColor.BluViola;
        if(s.equals("ArancioGiallo"))
            this.color1= IttenColor.ArancioGiallo;
        if(s.equals("Viola"))
            this.color1= IttenColor.Viola;
        if(s.equals("Giallo"))
            this.color1= IttenColor.Giallo;
        if(s.equals("VerdeLime"))
            this.color1= IttenColor.VerdeLime;
        if(s.equals("Cremisi"))
            this.color1= IttenColor.Cremisi;
        if(s.equals("Rosso"))
            this.color1= IttenColor.Rosso;
        if(s.equals("Verde"))
            this.color1= IttenColor.Verde;
        if(s.equals("RossoArancio"))
            this.color1= IttenColor.RossoArancio;
        if(s.equals("Turchese"))
            this.color1= IttenColor.Turchese;
    }

    public void setColor2AsString(String s){
        if(s.equals("Jeans"))
            this.color2= IttenColor.Jeans;
        if(s.equals("Grigio"))
            this.color2= IttenColor.Grigio;
        if(s.equals("Marrone"))
            this.color2= IttenColor.Marrone;
        if(s.equals("BluNavy"))
            this.color2= IttenColor.BluNavy;
        if(s.equals("Nero"))
            this.color2= IttenColor.Nero;
        if(s.equals("Bianco"))
            this.color2= IttenColor.Bianco;
        if(s.equals("Blu"))
            this.color2= IttenColor.Blu;
        if(s.equals("Arancio"))
            this.color2= IttenColor.Arancio;
        if(s.equals("BluViola"))
            this.color2= IttenColor.BluViola;
        if(s.equals("ArancioGiallo"))
            this.color2= IttenColor.ArancioGiallo;
        if(s.equals("Viola"))
            this.color2= IttenColor.Viola;
        if(s.equals("Giallo"))
            this.color2= IttenColor.Giallo;
        if(s.equals("VerdeLime"))
            this.color2= IttenColor.VerdeLime;
        if(s.equals("Cremisi"))
            this.color2= IttenColor.Cremisi;
        if(s.equals("Rosso"))
            this.color2= IttenColor.Rosso;
        if(s.equals("Verde"))
            this.color2= IttenColor.Verde;
        if(s.equals("RossoArancio"))
            this.color2= IttenColor.RossoArancio;
        if(s.equals("Turchese"))
            this.color2= IttenColor.Turchese;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int[] getStagioni(){
        return this.stagioni;
    }

    public String getStagioniAsString(){
        String s="";
        if(stagioni[0]==1)
            s=s+" Inverno ";
        if(stagioni[1]==1)
            s=s+" Autunno ";
        if(stagioni[2]==1)
            s=s+" Primavera ";
        if(stagioni[3]==1)
            s=s+" Estate ";


        return s;
    }

    public int getDecimalStagioni(){

        int result=0;

        result=stagioni[0]+stagioni[1]*2+stagioni[2]*4+stagioni[3]*8;

        return result;
    }




    public void setStagione(String stagione){
        if(stagione.equalsIgnoreCase("inverno"))
            this.stagioni[0]=1;
        if(stagione.equalsIgnoreCase("autunno"))
            this.stagioni[1]=1;
        if(stagione.equalsIgnoreCase("primavera"))
            this.stagioni[2]=1;
        if(stagione.equalsIgnoreCase("estate"))
            this.stagioni[3]=1;
    }

    public int[] getOccasioni(){
        return this.occasioni;
    }

    public String getOccasioniAsString(){
        String s="";
        if(occasioni[0]==1)
            s=s+" Sportivo ";
        if(occasioni[1]==1)
            s=s+" Casual ";
        if(occasioni[2]==1)
            s=s+" Elegante ";


        return s;


    }

    public void setOccasioni(int[] occ){
         this.occasioni = occ;
    }

    public void setOccasione(String occasione) {
        if (occasione.equalsIgnoreCase("Sportivo"))
            this.occasioni[0] = 1;
        if (occasione.equalsIgnoreCase("Casual"))
            this.occasioni[1] = 1;
        if (occasione.equalsIgnoreCase("Elegante"))
            this.occasioni[2] = 1;

    }

    public void setCondizioni(int[] cond){
         this.condizioni = cond;
    }

    public String getCondizioniAsString(){
        String s="";
        if(condizioni[0]==1)
            s=s+" Sole ";
        if(condizioni[1]==1)
            s=s+" Pioggia ";
        if(condizioni[2]==1)
            s=s+" Vento ";
        if(condizioni[3]==1)
            s=s+" Neve ";


        return s;
    }



    public void setCondizioni(String cond) {
        if (cond.equalsIgnoreCase("Sole"))
            this.condizioni[0] = 1;
        if (cond.equalsIgnoreCase("pioggia"))
            this.condizioni[1] = 1;
        if (cond.equalsIgnoreCase("vento"))
            this.condizioni[2] = 1;
        if (cond.equalsIgnoreCase("neve"))
            this.condizioni[3] = 1;
    }

    public void setStagioni(int[] stagioni){
         this.stagioni = stagioni;
    }


    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(categoria.toString()+"\n");
        sb.append(tipo+"\n");
        sb.append(color1.toString()+"\n");
        sb.append(marca + "\n");
        sb.append(getOccasioniAsString() +"\n");
        sb.append(getStagioniAsString()+"\n");
        sb.append(getCondizioniAsString()+"\n");
        return sb.toString();
    }



}


