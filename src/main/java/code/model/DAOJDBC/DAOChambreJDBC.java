package code.model.DAOJDBC;

import code.*;
import code.model.ConnexionUnique;
import code.model.DAOInterfaces.DAOChambre;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

/**
 * Created by Vincent on 03/05/2019.
 */
public class DAOChambreJDBC implements DAOChambre {

    private static Connection connection = ConnexionUnique.getInstance().getConnection();
    private Set<String> typesChambres = getTypeChambres();

    @Override
    public boolean deleteHistoriqueChambre(Chambre chambre) {
        try {
            if(chambre != null) {
                String query = "DELETE FROM HistoriqueChambre WHERE num_c = ? AND num_h = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, chambre.getNumChambre());
                statement.setInt(2, chambre.getNumHotel());
                int nb = statement.executeUpdate();
                if(nb < 0) throw new SQLException("delete HistoriqueChambre didnt work");

                return nb > 0;

            } else throw new NullPointerException("Delete historique from null chambre ?");
        } catch (Exception e) {
            System.err.println("DAOChambreJDBC.deleteHistoriqueChambre");;
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReservationChambre(Chambre chambre) {
        try {
            if(chambre != null) {
                String query = "DELETE FROM ReservationChambre WHERE num_c = ? AND num_h = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, chambre.getNumChambre());
                statement.setInt(2, chambre.getNumHotel());
                int nb = statement.executeUpdate();
                if(nb < 0) throw new SQLException("delete ReservationChambre didnt work");

                return nb > 0;

            } else throw new NullPointerException("Delete ReservationChambre from null chambre ?");
        } catch (Exception e) {
            System.err.println("DAOChambreJDBC.deleteReservationChambre");;
            e.printStackTrace();
        }
        return false;
    }

    @Override
    //TODO : D'ABORD DELETE LES OCCURENCES DE CHAMBRE DANS LES ASSOCIATIONS (HISTORIQUE, RESERVATIONCHAMBRE)
    public boolean delete(Chambre obj) {
        if(!(obj == null)) {
            String deleteQuery = "DELETE FROM Chambre WHERE num_c=?";
            try {
                PreparedStatement ps = connection.prepareStatement(deleteQuery);
                ps.setInt(1, obj.getNumChambre());
                int nb = ps.executeUpdate();

                ps.close();
                return (nb == 1);

            } catch (SQLException sqle) {
                System.err.println("DAOChambreJDBC.delete");
                sqle.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public EtatChambre getEtatChambre(Pair<Integer, Integer> idChambre) {
        String getEtatChambreQuery = "SELECT * FROM Historique H JOIN HistoriqueChambre HC ON H.num_hist = HC.num_hist WHERE num_h = ? AND num_c = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(getEtatChambreQuery);
            ps.setInt(1, idChambre.getKey());
            ps.setInt(2, idChambre.getValue());
            ResultSet rs = ps.executeQuery();

            EtatChambre etatChambre = new EtatChambre();
            boolean estIndisponible = false;
            while (rs.next()) {
                Date currentDate = new Date();
                if (currentDate.after(rs.getDate("date_deb")) && currentDate.before(rs.getDate("date_fin"))) {
                    estIndisponible = true;
                    etatChambre.setNomEtat(rs.getString("type_hist"));
                    etatChambre.setDateDebut(rs.getDate("date_deb").toLocalDate());
                    etatChambre.setDateFin(rs.getDate("date_fin").toLocalDate());
                }
            }
            if (!estIndisponible) {
                etatChambre.setNomEtat("OPEN");
            }

            return etatChambre;

        } catch (SQLException sqle) {
            System.err.println("DAOChambreJDBC.getEtatChambre");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Chambre createChambre(ResultSet resultSetChambres) {
        try {
            String query2 = "SELECT * FROM TypeChambre WHERE nom_t = ?";
            String typeChambre = resultSetChambres.getString("nom_t");
            PreparedStatement ps = connection.prepareStatement(query2);
            ps.setString(1, typeChambre);
            ResultSet rsTypeChambre = ps.executeQuery();

            if (rsTypeChambre.next()) {
                int numChambre = resultSetChambres.getInt("num_c");
                //TODO : VOIR SI LE NUMERO D HOTEL EST SUFFISANT DANS CHAMBRE POUR EVITER PB DE BOUCLE INFINIE
                int numHotel = resultSetChambres.getInt("num_h");

                EtatChambre etat = getEtatChambre(new Pair<>(numHotel, numChambre));

                Chambre chambre = new Chambre(numChambre, numHotel, etat, typeChambre, rsTypeChambre.getInt("nbLits_t"), rsTypeChambre.getFloat("prix_t"));

                if (rsTypeChambre.getBoolean("telephone_t")) {
                    chambre.addOption(new OptionTelephone());
                }
                if (rsTypeChambre.getBoolean("tv_t")) {
                    chambre.addOption(new OptionTV());
                }
                ps.close();
                return chambre;
            }
        } catch (SQLException sqle) {
            System.err.println("DAOChambreJDBC.createChambre");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Chambre> findAll() {

        String query1 = "SELECT * FROM Chambre";
        try {
            ResultSet rsChambre = connection.createStatement().executeQuery(query1);
            List<Chambre> chambres = new ArrayList<>();

            while (rsChambre.next()) {
                chambres.add(createChambre(rsChambre));
            }
            return chambres;
        } catch(SQLException sqle) {
            System.err.println("DAOChambreJDBC.findAll");
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public Chambre getById(Pair<Integer, Integer> id) {
        if (id.getKey() != null && id.getValue() != null) {
            String getByIdQuery = "SELECT * FROM Chambre WHERE num_h = ? AND num_c = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(getByIdQuery);
                ps.setInt(1, id.getKey());
                ps.setInt(2, id.getValue());
                ResultSet rsChambre = ps.executeQuery();

                if(rsChambre.next()) {
                    return createChambre(rsChambre);
                }
            } catch (SQLException sqle) {
                System.err.println("DAOChambreJDBC.getById");
                sqle.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void insertEntreeHistorique(Chambre obj) {
        String insertHistoriqueQuery = "INSERT INTO Historique(type_hist, date_deb, date_fin) VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insertHistoriqueQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getEtat().getNomEtat());
            ps.setDate(2, java.sql.Date.valueOf(obj.getEtat().getDateDebut()));
            ps.setDate(3, java.sql.Date.valueOf(obj.getEtat().getDateFin()));

            int resultInsertHistorique = ps.executeUpdate();

            if (resultInsertHistorique == 0) {
                throw new SQLException("Insert Historique echouee");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            ps.close();
            if (generatedKeys.next()) {
                int lastInsertedId = generatedKeys.getInt(1);
                String insertHistoriqueChambreQuery = "INSERT INTO HistoriqueChambre(num_h, num_c, num_hist) VALUES(?,?,?)";
                PreparedStatement ps2 = connection.prepareStatement(insertHistoriqueChambreQuery);
                ps2.setInt(1, obj.getNumHotel());
                ps2.setInt(2, obj.getNumChambre());
                ps2.setInt(3, lastInsertedId);

                int resultInsertChambreHistorique = ps2.executeUpdate();

                if (resultInsertChambreHistorique == 0) {
                    throw new SQLException("Insert Historique echouee");
                }

            }

        } catch (SQLException sqle) {
            System.err.println("DAOChambreJDBC.insertEntreeHistorique");
            sqle.printStackTrace();
        }
    }

    @Override
    public Chambre insert(Chambre obj) {
        if(obj != null && getTypeChambres().contains(obj.getType())){
            String insertQuery = "INSERT INTO Chambre(num_h, num_c, nom_t) VALUES(?,?,?)";
            try{
                PreparedStatement ps = connection.prepareStatement(insertQuery);
                ps.setInt(1, obj.getNumHotel());
                ps.setInt(2, obj.getNumChambre());
                ps.setString(3, obj.getType());
                int nb = ps.executeUpdate();
                ps.close();

                if (obj.getEtat().getDateDebut() != null) {
                    insertEntreeHistorique(obj);
                }

                return (nb == 1) ? obj : null;

            } catch (SQLException sqle) {
                System.out.println("DAOChambreJDBC.insert()");
                sqle.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean update(Chambre obj) {
        if(obj != null && typesChambres.contains(obj.getType())){
            //si la chambre existe déjà
            if (getById(new Pair<>(obj.getNumHotel(), obj.getNumChambre())) != null) {
                String updateQuery = "UPDATE Chambre SET num_h=?, num_c=?, nom_t=? WHERE num_h=? AND num_c=?";
                try {
                    PreparedStatement ps = connection.prepareStatement(updateQuery);
                    ps.setInt(1, obj.getNumHotel());
                    ps.setInt(2, obj.getNumChambre());
                    ps.setString(3, obj.getType());
                    ps.setInt(4,obj.getNumHotel());
                    ps.setInt(5, obj.getNumChambre());
                    int nb = ps.executeUpdate();
                    ps.close();

                    if (obj.getEtat().getDateDebut() != null) {
                        insertEntreeHistorique(obj);
                    }
                } catch (SQLException sqle) {
                    System.err.println("DAOChambreJDBC.update");
                    sqle.printStackTrace();
                }
            } else {
                insert(obj);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getNbChambres() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(*) FROM Chambre");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException sqle) {
            System.err.println("DAOChambreJDBC.getNbChambres");
            sqle.printStackTrace();
        }
        return -1;
    }

    public Set<String> getTypeChambres() {
        Set<String> types = new HashSet<>();
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery("SELECT nom_t FROM TypeChambre");
            while(rs.next()) {
                types.add(rs.getString("nom_t"));
            }
            return types;
        } catch (SQLException sqle) {
            System.err.println("DAOChambreJDBC.getTypeChambres");
            sqle.printStackTrace();
        }
       return null;
    }
}
