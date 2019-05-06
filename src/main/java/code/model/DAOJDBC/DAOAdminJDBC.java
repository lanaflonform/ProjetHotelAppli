package code.model.DAOJDBC;

import code.Admin;
import code.Hotel;
import code.TypeAcces;
import code.TypeService;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOAdmin;

import java.sql.*;
import java.util.*;

/**
 * Created by Vincent on 01/05/2019.
 */
public class DAOAdminJDBC implements DAOAdmin {

    private static Connection connection = ConnexionUnique.getInstance().getConnection();
    private static List<TypeAcces> typesAcces = new DAOTypeAccesJDBC().findAll();

    @Override
    public boolean delete(Admin obj) {
        if(obj != null) {
            String deleteQuery = "DELETE FROM Admin WHERE num_a=?";
            try {
                PreparedStatement ps = connection.prepareStatement(deleteQuery);
                ps.setInt(1, obj.getNumAdmin());
                int deleteAdminResult = ps.executeUpdate();

                if (deleteAdminResult == 0) {
                    throw new SQLException("Insertion admin echouee");
                }

                return (deleteAdminResult == 1);

            } catch (SQLException sqle) {
                System.err.println("DAOAdminJDBC.delete");
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Map<String, Boolean> initDroits() {
        Map<String, Boolean> droits = new HashMap<>();
        for (TypeAcces typeAcces : typesAcces) {
            droits.put(typeAcces.getTypeAcces(), false);
        }
        return droits;
    }

    @Override
    public Map<String, Boolean> getDroits(int numAdmin) {
        String queryGetDroits = "SELECT T.* FROM TypeAcces AS T JOIN Posseder AS P ON T.nom_a = P.nom_a";
        queryGetDroits += " WHERE num_a = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(queryGetDroits);
            ps.setInt(1, numAdmin);
            ResultSet resultGetDroits = ps.executeQuery();

            Map<String, Boolean> droits = initDroits();
            while(resultGetDroits.next()) {
                droits.put(resultGetDroits.getString("nom_a"), true);
            }

            return droits;
        } catch (SQLException sqle) {
            System.err.println("DAOAdminJDBC.getDroits");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        String query = "SELECT * FROM Admin";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            List<Admin> admins = new ArrayList<>();
            while (resultSet.next()) {

                Map<String, Boolean> droits = getDroits(resultSet.getInt("num_a"));

                admins.add(new Admin (
                        resultSet.getInt("num_a"),
                        resultSet.getString("identifiant"),
                        resultSet.getString("motdepasse"),
                        droits
                ));
            }
            return admins;
        } catch(SQLException sqle) {
            System.err.println("DAOAdminJDBC.findAll");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin getById(Integer id) {
        if (id != null) {
            String getByIdQuery = "SELECT * FROM Admin where num_a = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(getByIdQuery);
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();

                if(resultSet.next()) {

                    Map<String, Boolean> droits = getDroits(resultSet.getInt("num_a"));

                    return new Admin(
                            resultSet.getInt("num_a"),
                            resultSet.getString("identifiant"),
                            resultSet.getString("motdepasse"),
                            droits);
                }
            } catch (SQLException sqle) {
                System.err.println("DAOAdminJDBC.getById");
                sqle.printStackTrace();
            }

        }
        return null;
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
    public void insertDroits(int numAdmin, Map<String, Boolean> droits) {
        if (droits.size() != 0) {
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
    }

    @Override
    public boolean update(Admin obj) {
        if(obj != null){
            String updateAdminQuery = "UPDATE Admin SET identifiant=?, motdepasse=? WHERE num_a=?";
            try {
                PreparedStatement ps = connection.prepareStatement(updateAdminQuery);
                ps.setString(1, obj.getIdentifiant());
                ps.setString(2, obj.getMdp());
                ps.setInt(3, obj.getNumAdmin());

                int updateAdminResult = ps.executeUpdate();

                if (updateAdminResult == 0) {
                    throw new SQLException("Update Admin echouee");
                }
                ps.close();

                return true;
            } catch (SQLException sqle) {
                System.err.println("DAOAdminJDBC.update");
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateDroits(Admin obj) {
        if (obj != null) {
            try {
                if (obj.getDroits().size() != 0) {
                    String deleteDroitssQuery = "DELETE FROM Posseder WHERE num_a = ?";

                    PreparedStatement ps = connection.prepareStatement(deleteDroitssQuery);
                    ps.setInt(1, obj.getNumAdmin());

                    int deletePossederResult = ps.executeUpdate();
                }
                insertDroits(obj.getNumAdmin(), obj.getDroits());
                return true;
            } catch (SQLException sqle) {
                System.err.println("DAOAdminJDBC.updateDroits");
                sqle.printStackTrace();
            }

        }
        return false;
    }

    @Override
    public Admin findByUsernameAndPassword(String username, String password) {
        try {
            Statement stmt = connection.createStatement();
            String req = "SELECT * FROM Admin WHERE identifiant='" +  username + "' AND motdepasse='" + password + "'";
            System.out.println(req);
            ResultSet rs = stmt.executeQuery(req);
            Admin admin = new Admin();
            if (rs.next()) {
                Map<String, Boolean> droits = getDroits(rs.getInt("num_a"));
                admin.setNumAdmin(rs.getInt("num_a"));
                admin.setIdentifiant(rs.getString("identifiant"));
                admin.setMdp(rs.getString("motdepasse"));
                admin.setDroits(droits);
                stmt.close();
                return admin;
            } else {
                return null;
            }

        } catch (SQLException sqle) {
            System.err.println("DAOAdminJDBC.findByUsernameAndPassword");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public int getNbAdmins() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(*) FROM Admin");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.err.println("DAOAdminJDBC.getNbAdmins");
            sqle.printStackTrace();
        }
        return -1;
    }
}
