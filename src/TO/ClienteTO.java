package TO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;

/**
 *
 * @author Sérgio Damião
 */
public class ClienteTO implements Serializable {
    private static final long serialVersionUID = -5275385798567097878L;

    private int idFornecedor;
    private String nome = "";
    private String endereco = "";
    private String cidade = "";
    private String UF = "";

}
