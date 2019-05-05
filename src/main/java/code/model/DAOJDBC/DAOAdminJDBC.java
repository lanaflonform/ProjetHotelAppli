package code.model.DAOJDBC;

import code.Admin;
import code.TypeAcces;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOAdmin;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Vincent on 01/05/2019.
 */
public class DAOAdminJDBC implements DAOAdmin {

    private static Connection connection = ConnexionUnique.getInstance().getConnection();
    private static List<TypeAcces> typesAcces = new DAOTypeAccesJDBC().findAll();

    @Override
    public boolean delete(Admin obj) {
        return false;
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public Admin getById(Integer integer) {
        return null;
    }

    @Override
    public void insertDroits(int numAdmin, Map<String, Boolean> droits) {

        String insertDroitsQuery = "INSERT INTO Posseder(num_a, nom_a) VALUES (?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertDroitsQuery);
            for (Map.Entry<String,Boolean> droit : droits.entrySet()) {
                if (droit.getValue()) {
                    statement.setInt(1, numAdmin);
                    statement.setString(2, droit.getKey());

                    int insertPossederResult = statement.executeUpdate();

                    if (insertPossederResult == 0) {
                        throw new SQLException("Insertion Posseder echouee");
                    }
                }
            }
        } catch (SQLException sqle) {
            System.err.println("DAOAdminJDBC.insertDroits");
            sqle.printStackTrace();
        }

    }

    @Override
    public Admin insert(Admin obj) {
        if(obj != null){
            String insertAdminQuery = "INSERT INTO Admin (identifiant, motdepasse) VALUES (?, ?)";
            try{
                PreparedStatement statement = connection.prepareStatement(insertAdminQuery, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, obj.getIdentifiant());
                statement.setString(2, obj.getMdp());

                int insertAdminResult = statement.executeUpdate();

                if (insertAdminResult == 0) {
                    throw new SQLException("Insertion admin echouee");
                }

                ResultSet generatedKeys = statement.getGeneratedKeys();
                statement.close();
                if (generatedKeys.next()) {
                    int lastInsertedId = generatedKeys.getInt(1);
                    insertDroits(lastInsertedId, obj.getDroits());
                    return obj;
                }

                return obj;

            } catch (SQLException sqle) {
                System.out.println("DAOAdminJDBC.insert()");
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
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
        } catch (SQLException sqle) {
            System.err.println("DAOAdminJDBC.findByUsernameAndPassword");
            sqle.printStackTrace();
        }
        return null;
    }
}
