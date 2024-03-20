package TO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Sérgio Damião
 */
public class TurmaTO implements Serializable {
    private static final long serialVersionUID = -5275385798567097878L;

    public static Collection<TurmaTO> buscaProfessor(String trim) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void buscaProfessor(TurmaTO idProf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int idturma;
    private String serie = "";
    private String numeroturma = "";

    public int getIdturma() {
        return idturma;
    }

    public void setIdturma(int idturma) {
        this.idturma = idturma;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumeroturma() {
        return numeroturma;
    }

    public void setNumeroturma(String numeroturma) {
        this.numeroturma = numeroturma;
    }

    
}
