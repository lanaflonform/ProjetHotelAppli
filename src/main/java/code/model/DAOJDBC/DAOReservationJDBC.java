package code.model.DAOJDBC;

import code.Chambre;
import code.Client;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOReservation;
import code.Reservation;
import com.mysql.jdbc.SQLError;
import javafx.util.Pair;

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

                synchronized (obj.getChambres()) {
                    List<Chambre> chambres = obj.getChambres();
                    if (chambres != null) {
                        for (Chambre chambre : obj.getChambres()) {
                            this.deleteLiens(obj, chambre);
                        }
                    }
                }

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, obj.getNumReservation());

                return (statement.executeUpdate() == 1);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Reservation> findAll() {

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
                    resultSet.getFloat("prixTotal_r"),
                    resultSet.getFloat("reduction_r"),
                    new DAOClientJDBC().getById(resultSet.getInt("num_cl")),
                    this.getChambres(resultSet.getInt("num_r"))
                ));
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

                Reservation reservation = new Reservation();
                reservation.setNumReservation(integer);

                while(resultSet.next()) {
                    reservation = new  Reservation (
                        resultSet.getInt("num_r"),
                        resultSet.getDate("dateAr_r").toLocalDate(),
                        resultSet.getDate("dateDep_r").toLocalDate(),
                        resultSet.getInt("nbPersonnes_r"),
                        resultSet.getString("etat_r"),
                        resultSet.getFloat("prixTotal_r"),
                        resultSet.getFloat("reduction_r"),
                        new DAOClientJDBC().getById(resultSet.getInt("num_cl")),
                        this.getChambres(resultSet.getInt("num_r"))
                    );
                }
                return reservation;
            } catch (SQLException sqle) {
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
                statement.setInt(1, obj.getNumReservation());
                statement.setString(2, obj.getDateArrivee().toString());
                statement.setString(3, obj.getDateDepart().toString());
                statement.setInt(4, obj.getNbPersonnes());
                statement.setString(5, obj.getEtat());
                statement.setFloat(6, obj.getReduction());
                statement.setInt(7, obj.getClient().getNum());

                int nb = statement.executeUpdate();

                if(obj.getChambres() != null) {
                    for(Chambre chambre: obj.getChambres()) {
                        if(!this.insertLiens(obj, chambre)){
                            throw new SQLException("inserting links reservation_chambre failed");
                        }
                    }
                }

                return (nb == 1) ? obj: null;

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
            String query = "UPDATE Reservation SET dateAr_r = ?, dateDep_r = ?, nbPersonnes_r = ?, etat_r = ?, prixTotal_r, reduction_r = ?, num_cl = ? WHERE num_r = ?";
            try{
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, obj.getDateArrivee().toString());
                statement.setString(2, obj.getDateDepart().toString());
                statement.setInt(3, obj.getNbPersonnes());
                statement.setString(4, obj.getEtat());
                statement.setFloat(5, obj.getPrixTotal());
                statement.setFloat(6, obj.getReduction());
                statement.setInt(7, obj.getNumReservation());
                statement.setInt(8, obj.getClient().getNum());

                return (statement.executeUpdate() == 1);

            } catch (SQLException sqle) {
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Chambre> getChambres(Integer integer) {
        try {
            if(integer != null) {
                String query = "SELECT num_c, num_h FROM ReservationChambre WHERE num_r = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, integer);
                ResultSet rset = preparedStatement.executeQuery();

                List<Chambre> chambres = new ArrayList<>();

                while (rset.next()) {
                    chambres.add(new DAOChambreJDBC().getById(new Pair<Integer, Integer>(rset.getInt("num_h"), rset.getInt("num_c"))));
                }
                return chambres;
            }
            throw new SQLException("No reservation");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteLiens(Reservation reservation, Chambre chambre) {
        if(reservation != null) {
            try {
                PreparedStatement statement;
                if(chambre != null) {
                    if(chambre.getHotel() != null) {
                        String query = "DELETE FROM ReservationChambre where num_c = ? AND num_h = ? AND num_r = ?";
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, chambre.getNumChambre());
                        statement.setInt(2, chambre.getHotel().getNumHotel());
                        statement.setInt(3, reservation.getNumReservation());
                    } else throw new NullPointerException("Try to delete chambre without num hotel");
                } else {
                    String query = "DELETE FROM ReservationChambre where num_r = ?";
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, reservation.getNumReservation());
                }
                int nb = statement.executeUpdate();

                if(nb < 1) {
                    throw new SQLException("Erreur suppressions");
                }
                return (nb >= 1);

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean insertLiens(Reservation reservation, Chambre chambre) {
        if(chambre != null && reservation != null){
            String query = "INSERT INTO ReservationChambre (num_c, num_h, num_r) VALUES (?, ?, ?)";
            try{
                if(chambre.getHotel() != null) {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, chambre.getNumChambre());
                    statement.setInt(2, chambre.getHotel().getNumHotel());
                    statement.setInt(3, reservation.getNumReservation());

                    return (statement.executeUpdate() == 1);

                } else throw new NullPointerException("Trying insert reservation_chambre with not hotel");


            } catch (SQLException sqle) {
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateLiens(Reservation reservation) {
        if (reservation != null) {
            try {
                if (!deleteLiens(reservation, null)) {
                    throw new SQLException("deleteing links reservation_chambre failed");
                }

                if (reservation.getChambres() != null) {
                    for (Chambre chambre : reservation.getChambres()) {
                        if (!this.insertLiens(reservation, chambre)) {
                            throw new SQLException("inserting links reservation_chambre failed");
                        }
                    }
                }
                return true;
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                sqle.printStackTrace();
            }
        }
        return false;
    }
}
