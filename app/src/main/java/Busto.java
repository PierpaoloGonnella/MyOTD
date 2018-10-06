/**
 * Created by pierpawel on 27/12/2015.
 */
public class Busto extends Capo {

    private int index;
    private BustoCat categoria;

    public Busto(Tipo tipo,BustoCat categoria, int index){
        super(tipo);
        this.index=index;
        this.categoria=categoria;
    }

    public int getIndex(){
        return index;
    }

    public BustoCat getCategoria(){
        return this.categoria;
    }

    public void setIndex(int i){
        this.index=i;
    }
}

