package code.model.DAOJDBC;

import code.Client;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOReservation;
import code.Reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOReservationJDBC implements DAOReservation {
    @Override
    public boolean delete(Reservation obj) {
        if(!(obj == null)) {
            String query = "DELETE FROM Reservation where num_r = " + obj.getNum();
            try {
                Connection connection  = ConnexionUnique.getInstance().getConnection();
                Statement statement = connection.createStatement();
                int nb = statement.executeUpdate(query);

                return (nb == 1);

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
            String query = "SELECT * FROM Reservation where num_r = " + integer;

            try {
                ResultSet resultSet = ConnexionUnique.getInstance().getConnection().createStatement().executeQuery(query);
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
                        + "VALUES (" + "\"" + obj.getNum() + "\""
                        + " , \"" + obj.getDateArrivee() + "\""
                        + ", \"" + obj.getDateDepart() + "\""
                        + ", " + obj.getNbPersonnes()
                        + ", \"" + obj.getEtat() + "\""
                        + ", \"" + obj.getReduction() + "\""
                        + ", " + /*obj.getClient().getNum()*/ 6
                        + ")";
                Connection connection = ConnexionUnique.getInstance().getConnection();
                Statement statement = connection.createStatement();
                int nb = statement.executeUpdate(query);

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
            try{
                String query = "UPDATE Reservation"
                        + " SET dateAr_r =  \"" + obj.getDateArrivee() + "\""
                        + ", dateDep_r = \"" + obj.getDateDepart() + "\""
                        + ", nbPersonnes_r = " + obj.getNbPersonnes()
                        + ", etat_r = \"" + obj.getEtat() + "\""
                        + ", reduction_r = \"" + obj.getReduction() + "\""
                        + ", num_cl = " + 6//obj.getClient().getNum()
                        + " WHERE num_r = " + obj.getNum();
                Connection connection = ConnexionUnique.getInstance().getConnection();
                Statement statement = connection.createStatement();
                int nb = statement.executeUpdate(query);

                return (nb == 1);

            } catch (SQLException sqle) {
                System.out.println("DAORESERVATIONJDBC.insert()");
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return false;
    }
}
