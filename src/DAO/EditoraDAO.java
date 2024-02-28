package DAO;

import TO.EditoraTO;


import db.DaoException;
import db.DataBaseDaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class EditoraDAO {

    java.util.Date hoje = new java.util.Date();


    protected static Connection getConnection() throws SQLException {
        return DataBaseDaoFactory.getConnection();
    }


    public static int inserirEditora(EditoraTO to) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        String INSERT_SQL
                = "INSERT INTO editora "
                + "(idEditora, nome) "
                + "VALUES('" + to.getIdEditora()+ "',"
                + " '" + to.getNome() + "') ";
        try {
            con = getConnection();
            ps = con.prepareStatement(INSERT_SQL);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT LAST_INSERT_ID() FROM editora");
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

    

    public static void deletarEditora(EditoraTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String DELETE_SQL = "DELETE FROM editora WHERE idEditora = '" + to.getIdEditora()+ "'";
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

    public static void alterarEditora(EditoraTO to) throws DaoException {
        Connection con;
        PreparedStatement ps;
        String UPDATE_SQL = "UPDATE editora SET  nome = '" + to.getNome() + "' "
                + "WHERE idEditora  = '" + to.getIdEditora()+ "'";
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

    public static Collection<EditoraTO> buscaEditoras(String parteNome) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String sql = "SELECT * FROM editora WHERE (UPPER(nome) LIKE UPPER('%" + parteNome + "%')) ORDER BY nome";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            ArrayList<EditoraTO> td = new ArrayList<>();

            while (rs.next()) {
                EditoraTO to = new EditoraTO();
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

    public static EditoraTO detalharEditora(int idEdt) throws DaoException {
        Connection con;
        PreparedStatement ps;
        try {
            String DETALHAR_SQL = "SELECT * FROM editora WHERE BINARY idEditora = " + idEdt;
            con = getConnection();
            ps = con.prepareStatement(DETALHAR_SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EditoraTO to = new EditoraTO();
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

    private static void toset(ResultSet rs, EditoraTO to) throws DaoException, SQLException {
        to.setIdEditora(rs.getInt("idEditora"));
        to.setNome(rs.getString("nome"));
    }


}
