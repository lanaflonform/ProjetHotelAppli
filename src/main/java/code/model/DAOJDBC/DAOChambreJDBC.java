package code.model.DAOJDBC;

import code.*;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOChambre;
import javafx.util.Pair;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vincent on 03/05/2019.
 */
public class DAOChambreJDBC implements DAOChambre {

    private static Connection con = ConnexionUnique.getInstance().getConnection();

    @Override
    public boolean delete(Chambre obj) {
        if(!(obj == null)) {
            String deleteQuery = "DELETE FROM Chambre WHERE num_c=?";
            try {
                PreparedStatement ps = con.prepareStatement(deleteQuery);
                ps.setInt(1, obj.getNumChambre());
                int nb = ps.executeUpdate();

                ps.close();
                return (nb == 1);

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Chambre> FindAll() {

        String query1 = "SELECT * FROM Chambre";
        try {
            ResultSet rsChambre = con.createStatement().executeQuery(query1);
            List<Chambre> chambres = new ArrayList<>();

            while (rsChambre.next()) {
                String query2 = "SELECT * FROM TypeChambre WHERE nom_t = ?";
                String typeChambre = rsChambre.getString("nom_t");
                PreparedStatement ps = con.prepareStatement(query2);
                ps.setString(1, typeChambre);
                ResultSet rsTypeChambre = ps.executeQuery();

                if (rsTypeChambre.next()) {
                    int numChambre = rsChambre.getInt("num_c");
                    Hotel hotel = (new DAOHotelJDBC().getById(rsChambre.getInt("num_h")));
                    String etat = rsChambre.getString("etat_c");
                    Chambre chambre = new Chambre(numChambre, hotel, etat, typeChambre, rsTypeChambre.getInt("nbLits_t"), rsTypeChambre.getFloat("prix_t"));

                    if (rsTypeChambre.getBoolean("telephone_t")) {
                        chambre.addOption(new OptionTelephone());
                    }
                    if (rsTypeChambre.getBoolean("tv_t")) {
                        chambre.addOption(new OptionTV());
                    }
                    ps.close();
                    chambres.add(chambre);
                }
            }
            return chambres;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;

    }

    @Override
    public Chambre getById(Pair<Integer, Integer> id) {
        if (id.getKey() != null && id.getValue() != null) {
            String getByIdQuery = "SELECT * FROM Chambre WHERE num_h = ? AND num_c = ?";
            try {
                PreparedStatement ps = con.prepareStatement(getByIdQuery);
                ps.setInt(1, id.getKey());
                ps.setInt(2, id.getValue());
                ResultSet rsChambre = ps.executeQuery();

                if(rsChambre.next()) {
                    String query2 = "SELECT * FROM TypeChambre WHERE nom_t = ?";
                    String typeChambre = rsChambre.getString("nom_t");
                    PreparedStatement ps2 = con.prepareStatement(query2);
                    ps2.setString(1, typeChambre);
                    ResultSet rsTypeChambre = ps2.executeQuery();

                    if (rsTypeChambre.next()) {
                        int numChambre = rsChambre.getInt("num_c");
                        Hotel hotel = (new DAOHotelJDBC().getById(rsChambre.getInt("num_h")));
                        String etat = rsChambre.getString("etat_c");
                        Chambre chambre = new Chambre(numChambre, hotel, etat, typeChambre, rsTypeChambre.getInt("nbLits_t"), rsTypeChambre.getFloat("prix_t"));

                        if (rsTypeChambre.getBoolean("telephone_t")) {
                            chambre.addOption(new OptionTelephone());
                        }
                        if (rsTypeChambre.getBoolean("tv_t")) {
                            chambre.addOption(new OptionTV());
                        }
                        ps.close();
                        ps2.close();
                        return chambre;
                    }
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Chambre insert(Chambre obj) {
        if(obj != null && getTypeChambres().contains(obj.getType())){
            String insertQuery = "INSERT INTO Chambre(num_h, num_c, etat_c, nom_t) VALUES(?,?,?,?)";
            try{
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setInt(1, obj.getHotel().getNumHotel());
                ps.setInt(2, obj.getNumChambre());
                ps.setString(3, obj.getEtat());
                ps.setString(4, obj.getType());
                int nb = ps.executeUpdate();
                ps.close();
                return (nb == 1) ? obj: null;

            } catch (SQLException sqle) {
                System.out.println("DAOHotelJDBC.insert()");
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean update(Chambre obj) {
        if(obj != null && getTypeChambres().contains(obj.getType())){
            String updateQuery = "UPDATE Chambre SET num_h=?, num_c=?, etat_c=?, nom_t=? WHERE num_h=? AND num_c=?";
            try {
                PreparedStatement ps = con.prepareStatement(updateQuery);
                ps.setInt(1, obj.getHotel().getNumHotel());
                ps.setInt(2, obj.getNumChambre());
                ps.setString(3, obj.getEtat());
                ps.setString(4, obj.getType());
                ps.setInt(5,obj.getHotel().getNumHotel());
                ps.setInt(6, obj.getNumChambre());
                int nb = ps.executeUpdate();
                ps.close();
                return (nb == 1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public int getNbChambres() {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM Chambre");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Set<String> getTypeChambres() {
        Set<String> types = new HashSet<>();
        ResultSet rs = null;
        try {
            rs = con.createStatement().executeQuery("SELECT nom_t FROM TypeChambre");
            while(rs.next()) {
                types.add(rs.getString("nom_t"));
            }
            return types;
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }
}
