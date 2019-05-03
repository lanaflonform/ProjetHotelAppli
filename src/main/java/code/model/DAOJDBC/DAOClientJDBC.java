package code.model.DAOJDBC;

import code.Client;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOClient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOClientJDBC implements DAOClient {
    @Override
    public boolean delete(Client obj) {
        if(!(obj == null)) {
            String query = "DELETE FROM Client where num_cl = " + obj.getNum();
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
    public List<Client> FindAll() {
        String query = "SELECT * FROM Client";
        try {
            ResultSet resultSet = ConnexionUnique.getInstance().getConnection().createStatement().executeQuery(query);

            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                clients.add(new Client (
                    resultSet.getInt("num_cl"),
                    resultSet.getString("nom_cl"),
                    resultSet.getString("prenom_cl"),
                    resultSet.getString("nomEntreprise"),
                    resultSet.getString("telephone_cl"),
                    resultSet.getString("mail_cl"),
                    resultSet.getString("pseudo"),
                    resultSet.getString("motdepasse")
                ));
            }
            return clients;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Client getById(Integer integer) {
        if(integer != null) {

            String query = "SELECT * FROM Client where num_cl = " + integer;

            try {
                ResultSet resultSet = ConnexionUnique.getInstance().getConnection().createStatement().executeQuery(query);

                Client client = null;

                while(resultSet.next()) {
                    return new Client (
                            resultSet.getInt("num_cl"),
                            resultSet.getString("nom_cl"),
                            resultSet.getString("prenom_cl"),
                            resultSet.getString("nomEntreprise"),
                            resultSet.getString("telephone_cl"),
                            resultSet.getString("mail_cl"),
                            resultSet.getString("pseudo"),
                            resultSet.getString("motdepasse")
                    );
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Client insert(Client obj) {
        if(!(obj== null)){
            try{
                String query = "INSERT INTO Client (nom_cl, prenom_cl, nomEntreprise, telephone_cl, mail_cl, pseudo, motdepasse)"
                        + "VALUES (" + "\"" + obj.getNom() + "\""
                        + " , \"" + obj.getPrenom() + "\""
                        + ", \"" + obj.getNomEnteprise() + "\""
                        + ", " + obj.getTelephone()
                        + ", \"" + obj.getMail() + "\""
                        + ", \"" + obj.getPseudo() + "\""
                        + ", \"" + obj.getMotDePasse() + "\""
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
    public boolean update(Client obj) {
        if(!(obj== null)){
            try{
                String query = "UPDATE Client"
                        + " SET nom_cl = " + "\"" + obj.getNom() + "\""
                        + ", prenom_cl =  \"" + obj.getPrenom() + "\""
                        + ", nomEntreprise = \"" + obj.getNomEnteprise() + "\""
                        + ", telephone_cl = " + obj.getTelephone()
                        + ", mail_cl = \"" + obj.getMail() + "\""
                        + ", pseudo = \"" + obj.getPseudo() + "\""
                        + ", motdepasse = \"" + obj.getMotDePasse() + "\""
                        + " WHERE num_cl = " + obj.getNum();
                System.out.println(query);
                Connection connection = ConnexionUnique.getInstance().getConnection();
                Statement statement = connection.createStatement();
                int nb = statement.executeUpdate(query);

                return (nb == 1);

            } catch (SQLException sqle) {
                System.out.println("DAOClientJDBC.insert()");
                sqle.getMessage();
                sqle.printStackTrace();
            }
        }
        return false;
    }
}
