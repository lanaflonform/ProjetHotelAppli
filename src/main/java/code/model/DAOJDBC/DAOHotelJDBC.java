package code.model.DAOJDBC;

import code.Client;
import code.Hotel;
import code.TypeService;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOHotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vincent on 03/05/2019.
 */
public class DAOHotelJDBC implements DAOHotel {

    private static Connection con = ConnexionUnique.getInstance().getConnection();

    @Override
    public boolean delete(Hotel obj) {
        if(!(obj == null)) {
            String deleteQuery = "DELETE FROM Hotel WHERE num_h=?";
            try {
                PreparedStatement ps = con.prepareStatement(deleteQuery);
                ps.setInt(1, obj.getNumHotel());
                int nb = ps.executeUpdate();

                return (nb == 1);

            } catch (SQLException sqle) {
                System.err.println("DAOHotelJDBC.delete");
                sqle.printStackTrace();
            }
        }
        return false;
    }

    public Set<TypeService> getServices(int numHotel) {

        String queryGetServices = "SELECT T.* FROM TypeService AS T JOIN Proposer AS P ON T.nom_s = P.nom_s";
        queryGetServices += " WHERE num_h = ?";

        try {
            PreparedStatement ps = con.prepareStatement(queryGetServices);
            ps.setInt(1, numHotel);
            ResultSet resultGetServices = ps.executeQuery();

            Set<TypeService> services = new HashSet<>();
            while(resultGetServices.next()) {
                services.add(new TypeService(resultGetServices.getString("nom_s"), resultGetServices.getFloat("prix_s")));
            }

            return services;
        } catch (SQLException sqle) {
            System.err.println("DAOHotelJDBC.getServices");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hotel> findAll() {
        String query = "SELECT * FROM Hotel";
        try {
            ResultSet resultSet = con.createStatement().executeQuery(query);

            List<Hotel> hotels = new ArrayList<>();
            while (resultSet.next()) {

                Set<TypeService> services = getServices(resultSet.getInt("num_h"));

                hotels.add(new Hotel (
                        resultSet.getInt("num_h"),
                        resultSet.getString("nom_h"),
                        resultSet.getString("ville_h"),
                        resultSet.getString("adresse_h"),
                        resultSet.getFloat("latitude_h"),
                        resultSet.getFloat("longitude_h"),
                        services
                ));
            }
            return hotels;
        } catch(SQLException sqle) {
            System.err.println("DAOHotelJDBC.findAll");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Hotel getById(Integer id) {
        if (id != null) {
            String getByIdQuery = "SELECT * FROM Hotel where num_h = ?";
            try {
                PreparedStatement ps = con.prepareStatement(getByIdQuery);
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();

                if(resultSet.next()) {

                    Set<TypeService> services = getServices(id);

                    return new Hotel (
                            resultSet.getInt("num_h"),
                            resultSet.getString("nom_h"),
                            resultSet.getString("ville_h"),
                            resultSet.getString("adresse_h"),
                            resultSet.getFloat("latitude_h"),
                            resultSet.getFloat("longitude_h"),
                            services
                    );
                }
            } catch (SQLException sqle) {
                System.err.println("DAOHotelJDBC.getById");
                sqle.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public Hotel insert(Hotel obj) {
        if(obj != null){
            String insertHotelQuery = "INSERT INTO Hotel(nom_h, ville_h, adresse_h, latitude_h, longitude_h) VALUES(?,?,?,?,?)";
            try{
                PreparedStatement ps = con.prepareStatement(insertHotelQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, obj.getNom());
                ps.setString(2, obj.getVille());
                ps.setString(3, obj.getAdresse());
                ps.setFloat(4, obj.getLatitude());
                ps.setFloat(5, obj.getLongitude());
                int insertHotelResult = ps.executeUpdate();

                if (insertHotelResult == 0) {
                    throw new SQLException("Insertion hotel echouee");
                }

                //pas besoin de vérifier que le type service existe bien car lors de la création d'un hotel
                //on fera un findAll de TypeService qu'on affichera sur l'interface admin
                ResultSet generatedKeys = ps.getGeneratedKeys();
                ps.close();
                if (generatedKeys.next()) {
                    int lastInsertedId = generatedKeys.getInt(1);
                    insertServices(lastInsertedId, obj.getServices());
                    return obj;
                }

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
        if(obj != null){
            String updateQuery = "UPDATE Hotel SET nom_h=?, ville_h=?, adresse_h=?, latitude_h=?, longitude_h=? WHERE num_h=?";
            try {
                PreparedStatement ps = con.prepareStatement(updateQuery);
                ps.setString(1, obj.getNom());
                ps.setString(2, obj.getVille());
                ps.setString(3, obj.getAdresse());
                ps.setFloat(4, obj.getLatitude());
                ps.setFloat(5, obj.getLongitude());
                ps.setInt(6, obj.getNumHotel());

                int updateHotelResult = ps.executeUpdate();

                if (updateHotelResult == 0) {
                    throw new SQLException("Update Hotel echouee");
                }
                ps.close();

                return true;
            } catch (SQLException sqle) {
                System.err.println("DAOHotelJDBC.update");
                sqle.printStackTrace();
            }
        }
        return false;
    }

    public void insertServices(int numHotel, Set<TypeService> services) {

        String insertServicesQuery = "INSERT INTO Proposer(nom_s, num_h) VALUES (?,?)";
        try {
            PreparedStatement ps2 = con.prepareStatement(insertServicesQuery);
            for (TypeService service : services) {
                ps2.setString(1, service.getNom());
                ps2.setInt(2, numHotel);
                int insertProposerResult = ps2.executeUpdate();

                if (insertProposerResult == 0) {
                    throw new SQLException("Insertion Proposer echouee");
                }
            }
        } catch (SQLException sqle) {
            System.err.println("DAOHotelJDBC.insertServices");
            sqle.printStackTrace();
        }
    }

    public boolean updateServices(Hotel obj) {
        if (obj != null) {
            try {
                if (obj.getServices().size() != 0) {
                    String deleteServicesQuery = "DELETE FROM Proposer WHERE num_h = ?";

                    PreparedStatement ps = con.prepareStatement(deleteServicesQuery);
                    ps.setInt(1, obj.getNumHotel());

                    int deleteProposerResult = ps.executeUpdate();

                    if (deleteProposerResult == 0) {
                        throw new SQLException("Delete Proposer echouee");
                    }
                }
                insertServices(obj.getNumHotel(), obj.getServices());
                return true;
            } catch (SQLException sqle) {
                System.err.println("DAOHotelJDBC.updateServices");
                sqle.printStackTrace();
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
        } catch (SQLException sqle) {
            System.err.println("DAOHotelJDBC.getNbHotels");
            sqle.printStackTrace();
        }
        return -1;
    }
}
