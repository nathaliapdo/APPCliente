package DAO;

import TO.StatusClienteTO;
import TO.VendedoresTO;
import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class StatusClienteDAO {

    java.util.Date hoje = new java.util.Date();

    private static final String DETALHAR_SQL = "SELECT * FROM vendedores WHERE BINARY codigo = ?";

    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static void inserirStatusCliente(StatusClienteTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO statuscliente "
                + "(sigla, descricao) "
                + "VALUES('" + to.getSigla()+ "',"
                + " '" + to.getDescricao() + "') ";
        System.out.println(INSERT_SQL);
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } 
    }

    

    public static void deletarVendedor(VendedoresTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM vendedores WHERE codigo = '" + to.getCodigo() + "'";
        try {
            con = getConnection();
            ps = con.prepareStatement(DELETE_SQL);
            ps.execute();
            con.close();
            ps.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static void alterarStatusCliente(StatusClienteTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE statuscliente SET sigla = '" + to.getSigla()+ "', "
                + "descricao = '" + to.getDescricao() + "' WHERE idstatus  = " + to.getIdStatus();
        try {
            con = getConnection();
            ps = con.prepareStatement(UPDATE_SQL);
            ps.executeUpdate();
            con.close();
            ps.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static Collection<StatusClienteTO> buscaStatusCliente (String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM statuscliente WHERE (UPPER(sigla) LIKE UPPER('%" + parteNome + "%')) ORDER BY sigla";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<StatusClienteTO> lista = new ArrayList<>();

            while (rs.next()) {
                StatusClienteTO to = new StatusClienteTO();
                to.setIdStatus(rs.getInt("idStatus"));
                to.setSigla(rs.getString("Sigla"));
                to.setDescricao(rs.getString("Descricao"));
                lista.add(to);
            }
            con.close();
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static StatusClienteTO detalharStatusCliente(int status) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM statuscliente WHERE idstatus = " + status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatusClienteTO to = new StatusClienteTO();
                to.setIdStatus(rs.getInt("idstatus"));
                to.setSigla(rs.getString("sigla"));
                to.setDescricao(rs.getString("descricao"));
                con.close();
                ps.close();
                return to;
            }
            con.close();
            ps.close();
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
 
}
