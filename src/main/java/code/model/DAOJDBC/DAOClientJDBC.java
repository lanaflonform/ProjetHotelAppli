package code.model.DAOJDBC;

import code.Client;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOClientJDBC implements DAOClient {

    private static Connection connection = ConnexionUnique.getInstance().getConnection();

    @Override
    public boolean delete(Client obj) {
        if(!(obj == null)) {
            String query = "DELETE FROM Client where num_cl = ?";
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
        if(!integer.equals("") && !integer.equals(null)) {
            String query = "SELECT * FROM Client where num_cl = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, integer);
                ResultSet resultSet = statement.executeQuery();

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
            String query = "INSERT INTO Client (nom_cl, prenom_cl, nomEntreprise, telephone_cl, mail_cl, pseudo, motdepasse) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try{
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, obj.getNom());
                statement.setString(2, obj.getPrenom());
                statement.setString(3, obj.getNomEnteprise());
                statement.setString(4, obj.getTelephone());
                statement.setString(5, obj.getMail());
                statement.setString(6, obj.getPseudo());
                statement.setString(7, obj.getMotDePasse());

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
    public boolean update(Client obj) {
        if(!(obj== null)){
            try{
                String query = "UPDATE Client SET nom_cl = ?, prenom_cl = ?, nomEntreprise = ?, telephone_cl = ?, mail_cl = ?, pseudo =  ?, motdepasse = ? WHERE num_cl = ?";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, obj.getNom());
                statement.setString(2, obj.getPrenom());
                statement.setString(3, obj.getNomEnteprise());
                statement.setString(4, obj.getTelephone());
                statement.setString(5, obj.getMail());
                statement.setString(6, obj.getPseudo());
                statement.setString(7, obj.getMotDePasse());
                statement.setInt(8, obj.getNum());

                return (statement.executeUpdate() == 1);

            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                sqle.printStackTrace();
            }
        }
        return false;
    }
}
