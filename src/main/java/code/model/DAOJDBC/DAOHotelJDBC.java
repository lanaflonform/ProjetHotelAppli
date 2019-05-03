package code.model.DAOJDBC;

import code.Client;
import code.Hotel;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOHotel;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 03/05/2019.
 */
public class DAOHotelJDBC implements DAOHotel {

    private static Connection con = ConnexionUnique.getInstance().getConnection();
    private static final String deleteQuery = "DELETE FROM Hotel WHERE num_h=?";
    private static final String getByIdQuery = "SELECT * FROM Hotel where num_h = ?";
    private static final String insertQuery = "INSERT INTO Hotel(nom_h, ville_h, adresse_h, latitude_h, longitude_h) VALUES(?,?,?,?,?)";
    private static final String updateQuery = "UPDATE Hotel SET nom_h=?, ville_h=?, adresse_h=?, latitude_h=?, longitude_h=? WHERE num_h=?";

    @Override
    public boolean delete(Hotel obj) {
        if(!(obj == null)) {
            try {
                PreparedStatement ps = ConnexionUnique.getInstance().getConnection().prepareStatement(deleteQuery);
                ps.setInt(1, obj.getNumHotel());
                int nb = ps.executeUpdate();

                return (nb == 1);

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Hotel> FindAll() {
        String query = "SELECT * FROM Hotel";
        try {
            ResultSet resultSet = con.createStatement().executeQuery(query);

            List<Hotel> hotels = new ArrayList<>();
            while (resultSet.next()) {
                hotels.add(new Hotel (
                        resultSet.getInt("num_h"),
                        resultSet.getString("nom_h"),
                        resultSet.getString("ville_h"),
                        resultSet.getString("adresse_h"),
                        resultSet.getFloat("latitude_h"),
                        resultSet.getFloat("longitude_h")
                ));
            }
            return hotels;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Hotel getById(Integer id) {
        if (id != null) {

            try {
                PreparedStatement ps = con.prepareStatement(getByIdQuery);
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();

                if(resultSet.next()) {
                    return new Hotel (
                            resultSet.getInt("num_h"),
                            resultSet.getString("nom_h"),
                            resultSet.getString("ville_h"),
                            resultSet.getString("adresse_h"),
                            resultSet.getFloat("latitude_h"),
                            resultSet.getFloat("longitude_h")
                    );
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Hotel insert(Hotel obj) {
        if(!(obj== null)){

            try{

                //"INSERT INTO Hotel(nom_h, ville_h, adresse_h, latitude_h, longitude_h) VALUES(?,?,?,?,?)"
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, obj.getNom());
                ps.setString(2, obj.getVille());
                ps.setString(3, obj.getAdresse());
                ps.setFloat(4, obj.getLatitude());
                ps.setFloat(5, obj.getLongitude());
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
    public boolean update(Hotel obj) {
        if(!(obj== null)){
            try {
                //"UPDATE Hotel SET nom_h=?, ville_h=?, adresse_h=?, latitude_h=?, longitude_h=? WHERE num_h=?"
                PreparedStatement ps = con.prepareStatement(updateQuery);
                ps.setString(1, obj.getNom());
                ps.setString(2, obj.getVille());
                ps.setString(3, obj.getAdresse());
                ps.setFloat(4, obj.getLatitude());
                ps.setFloat(5, obj.getLongitude());
                ps.setInt(6, obj.getNumHotel());

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
    public int getNbHotels() {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM Hotel");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
