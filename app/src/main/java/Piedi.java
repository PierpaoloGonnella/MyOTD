/**
 * Created by pierpawel on 27/12/2015.
 */
public class Piedi extends Capo {

    private int index;
    private PiediCat categoria;

    public Piedi(Tipo tipo,PiediCat categoria, int index){
        super(tipo);
        this.index=index;
        this.categoria=categoria;
    }

    public int getIndex(){
        return index;
    }

    public PiediCat getCategoria(){
        return this.categoria;
    }

    public void setIndex(int i){
        this.index=i;
    }
}

