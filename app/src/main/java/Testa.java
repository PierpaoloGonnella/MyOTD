/**
 * Created by pierpawel on 27/12/2015.
 */
public class Testa extends Capo{

    private int index;
    private TestaCat categoria;

    public Testa(Tipo tipo,TestaCat categoria, int index){
        super(tipo);
        this.index=index;
        this.categoria=categoria;
    }

    public int getIndex(){
        return index;
    }

    public TestaCat getCategoria(){
        return this.categoria;
    }

    public void setIndex(int i){
        this.index=i;
    }
}
