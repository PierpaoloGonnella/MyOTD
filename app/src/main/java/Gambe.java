/**
 * Created by pierpawel on 27/12/2015.
 */
public class Gambe extends Capo {

    private int index;
    private GambeCat categoria;

    public Gambe(Tipo tipo,GambeCat categoria, int index){
        super(tipo);
        this.index=index;
        this.categoria=categoria;
    }

    public int getIndex(){
        return index;
    }

    public GambeCat getCategoria(){
        return this.categoria;
    }

    public void setIndex(int i){
        this.index=i;
    }
}

