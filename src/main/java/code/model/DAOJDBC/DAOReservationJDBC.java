package code.model.DAOJDBC;

import code.Client;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOReservation;
import code.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOReservationJDBC implements DAOReservation {

    private static Connection connection = ConnexionUnique.getInstance().getConnection();

    @Override
    public boolean delete(Reservation obj) {
        if(!(obj == null)) {
            String query = "DELETE FROM Reservation where num_r = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, obj.getNum());

                return (statement.executeUpdate() == 1);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Reservation> FindAll() {

        String query = "SELECT * FROM Reservation";
        try {
            ResultSet resultSet = ConnexionUnique.getInstance().getConnection().createStatement().executeQuery(query);

            List<Reservation> reservations = new ArrayList<>();
            while (resultSet.next()) {
                reservations.add(new Reservation (
                    resultSet.getInt("num_r"),
                    resultSet.getDate("dateAr_r").toLocalDate(),
                    resultSet.getDate("dateDep_r").toLocalDate(),
                    resultSet.getInt("nbPersonnes_r"),
                    resultSet.getString("etat_r"),
                    resultSet.getFloat("reduction_r"),
                    new DAOClientJDBC().getById(resultSet.getInt("num_cl")),
                    null,
                    null)
                );
            }
            return reservations;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Reservation getById(Integer integer) {
        if(!integer.equals("") && !integer.equals(null)) {
            String query = "SELECT * FROM Reservation where num_r = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, integer);
                ResultSet resultSet = statement.executeQuery();

                Reservation reservation = null;

                while(resultSet.next()) {
                    reservation = new Reservation (
                        resultSet.getInt("num_r"),
                        resultSet.getDate("dateAr_r").toLocalDate(),
                        resultSet.getDate("dateDep_r").toLocalDate(),
                        resultSet.getInt("nbPersonnes_r"),
                        resultSet.getString("etat_r"),
                        resultSet.getFloat("reduction_r"),
                        new DAOClientJDBC().getById(resultSet.getInt("num_cl")),
                        null,
                        null
                    );
                }
                return reservation;
            } catch (SQLException sqle) {
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Reservation insert(Reservation obj) {
        if(!(obj== null)){
            try{
                String query = "INSERT INTO Reservation (num_r, dateAr_r, dateDep_r, nbPersonnes_r, etat_r, reduction_r, num_cl)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, obj.getNum());
                statement.setString(2, obj.getDateArrivee().toString());
                statement.setString(3, obj.getDateDepart().toString());
                statement.setInt(4, obj.getNbPersonnes());
                statement.setString(5, obj.getEtat());
                statement.setFloat(6, obj.getReduction());
                statement.setInt(7, obj.getClient().getNum());

                return (statement.executeUpdate() == 1) ? obj: null;

            } catch (SQLException sqle) {
                System.out.println("DAOClientJDBC.insert()");
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean update(Reservation obj) {
        if(!(obj== null)){
            String query = "UPDATE Reservation SET dateAr_r = ?, dateDep_r = ?, nbPersonnes_r = ?, etat_r = ?, reduction_r = ?, num_cl = ? WHERE num_r = ?";
            try{
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, obj.getDateArrivee().toString());
                statement.setString(2, obj.getDateDepart().toString());
                statement.setInt(3, obj.getNbPersonnes());
                statement.setString(4, obj.getEtat());
                statement.setFloat(5, obj.getReduction());
                statement.setInt(6, obj.getNum());
                statement.setInt(7, obj.getClient().getNum());

                return (statement.executeUpdate() == 1);

            } catch (SQLException sqle) {
                System.out.println("DAORESERVATIONJDBC.insert()");
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return false;
    }
}
