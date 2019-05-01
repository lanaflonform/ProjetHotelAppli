package code.model.DAOJDBC;

import code.Admin;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOAdmin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Vincent on 01/05/2019.
 */
public class DAOAdminJDBC implements DAOAdmin {

    @Override
    public boolean delete(Admin obj) {
        return false;
    }

    @Override
    public List<Admin> FindAll() {
        return null;
    }

    @Override
    public Admin getById(Integer integer) {
        return null;
    }

    @Override
    public Admin insert(Admin obj) {
        return null;
    }

    @Override
    public boolean update(Admin obj) {
        return false;
    }

    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        Connection conn = ConnexionUnique.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            String req = "SELECT * FROM Admin WHERE identifiant='" +  username + "' AND motdepasse='" + password + "'";
            System.out.println(req);
            ResultSet rs = stmt.executeQuery(req);
            Admin admin = new Admin();
            if (rs.next()) {
                admin.setNumAdmin(rs.getInt("num_a"));
                admin.setIdentifiant(rs.getString("identifiant"));
                admin.setMdp(rs.getString("motdepasse"));
            }
            stmt.close();
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
