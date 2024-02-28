package DAO;

import TO.EditoraTO;
import TO.FornecedorTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class FornecedorDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirFornecedor(FornecedorTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO fornecedor "
                + "(idFornecedor, nome, endereco) "
                + "VALUES('" + to.getIdFornecedor()+ "',"
                + " '" + to.getNome() + "'," 
                + " '" + to.getEndereco() + "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM fornecedor");
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

    

    public static void deletarFornecedor(FornecedorTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM fornecedor WHERE idFornecedor = '" + to.getIdFornecedor()+ "'";
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

    public static void alterarFornecedor(FornecedorTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE fornecedor SET  "
                + "nome = '" + to.getNome()+ "', "
                + "endereco = '" + to.getEndereco()+ "' "
                + "WHERE idFornecedor  = '" + to.getIdFornecedor()+ "'";
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

    public static ArrayList<FornecedorTO> buscaFornecedor(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM fornecedor WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<FornecedorTO> td = new ArrayList<>();

            while (rs.next()) {
                FornecedorTO to = new FornecedorTO();
                toset(rs, to);
                td.add(to);
            }
            con.close();
            ps.close();
            return td;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static FornecedorTO detalharFornecedor(int idFor) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM fornecedor WHERE BINARY idfornecedor = " + idFor;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FornecedorTO to = new FornecedorTO();
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

    private static void toset(ResultSet rs, FornecedorTO to) throws DaoException, SQLException {
        to.setIdFornecedor(rs.getInt("idFornecedor"));
        to.setNome(rs.getString("nome"));
        to.setEndereco(rs.getString("endereco"));
    }


}
