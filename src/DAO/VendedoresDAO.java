package DAO;

import TO.VendedoresTO;
import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class VendedoresDAO {

    java.util.Date hoje = new java.util.Date();

    private static final String DETALHAR_SQL = "SELECT * FROM vendedores WHERE BINARY codigo = ?";

    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirVendedor(VendedoresTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO vendedores "
                + "(codigo, nome, percentual) "
                + "VALUES('" + to.getCodigo() + "',"
                + " '" + to.getNome() + "', "
                + " " + to.getPercentual() + ") ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM vendedores");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                con.close();
                ps.close();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
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

    public static void alterarVendedor(VendedoresTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE vendedores SET  nome = '" + to.getNome() + "', "
                + "percentual = " + to.getPercentual() + " WHERE codigo  = '" + to.getCodigo() + "'";
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

    public static Collection<VendedoresTO> buscaVendedores(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM vendedores WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<VendedoresTO> lista = new ArrayList<>();

            while (rs.next()) {
                VendedoresTO to = new VendedoresTO();
                toset(rs, to);
                lista.add(to);
            }
            con.close();
            ps.close();
            return lista;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static VendedoresTO detalharVendedor(int codigo) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM vendedores WHERE  codigo = " + codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VendedoresTO to = new VendedoresTO();
                toset(rs, to);
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

    private static void toset(ResultSet rs, VendedoresTO to) throws DaoException, SQLException {
        to.setCodigo(rs.getInt("codigo"));
        to.setNome(rs.getString("nome"));
        to.setPercentual(rs.getDouble("percentual"));
    }


    public static VendedoresTO buscaVendedorOutros() throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM vendedores WHERE (UPPER(nome) LIKE UPPER('OUTROS'))";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            VendedoresTO vnd = new VendedoresTO();

            if (rs.next()) {
                toset(rs, vnd);
            } else {
                vnd = null;
            }
            con.close();
            ps.close();
            return vnd;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    
    public static boolean existeCodigoVendedor(int codigo) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM vendedores WHERE (codigo = " + codigo + ")";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean resultado;
            VendedoresTO vnd = new VendedoresTO();

            if (rs.next()) {
                resultado =  true;
            } else {
                resultado = false;
            }
            con.close();
            ps.close();
            return resultado;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
        


}
