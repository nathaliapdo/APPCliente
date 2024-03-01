package DAO;

import TO.ClienteTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ClienteDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirCliente(ClienteTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO cliente "
                + "(idCliente, nome, endereco, cidade, UF) "
                + "VALUES('" + to.getIdCliente()+ "',"
                + " '" + to.getNome()+ "'," 
                + " '" + to.getEndereco()+ "'," 
                + " '" + to.getCidade()+ "'," 
                + " '" + to.getUF()+ "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM cliente");
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

    

    public static void deletarCliente(ClienteTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM cliente WHERE idCliente = '" + to.getIdCliente()+ "'";
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

    public static void alterarCliente(ClienteTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE cliente SET  "
                + "nome = '" + to.getNome()+ "', "
                + "endereco = '" + to.getEndereco()+ "', "
                + "cidade = '" + to.getCidade()+ "', "
                + "UF = '" + to.getUF()+ "' "
                + "WHERE idCliente  = '" + to.getIdCliente()+ "'";
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

    public static ArrayList<ClienteTO> buscaCliente(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            System.out.println("++++++ passei aqui 1");
            String sql = "SELECT * FROM cliente WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
             System.out.println("++++++ sql " + sql);
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<ClienteTO> td = new ArrayList<>();

            while (rs.next()) {
                ClienteTO to = new ClienteTO();
                toset(rs, to);
                td.add(to);
            }
            con.close();
            ps.close();
            return td;
        } catch (SQLException e) {
            System.out.println("++++++" + e.getMessage());
            throw new DaoException(e);
        }
    }

    public static ClienteTO detalharCliente(int idCli) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM cliente WHERE BINARY idCliente = " + idCli;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClienteTO to = new ClienteTO();
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

    private static void toset(ResultSet rs, ClienteTO to) throws DaoException, SQLException {
        to.setIdCliente(rs.getInt("idCliente"));
        to.setNome(rs.getString("nome"));
        to.setEndereco(rs.getString("endereco"));
        to.setCidade(rs.getString("cidade"));
        to.setUF(rs.getString("UF"));
    }


}
