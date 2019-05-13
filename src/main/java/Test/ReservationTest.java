package Test;

import code.Chambre;
import code.Client;
import code.Reservation;
import code.model.DAOJDBC.DAOChambreJDBC;
import code.model.DAOJDBC.DAOReservationJDBC;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationTest {
    public static void main(String args[]) {
        //testInsert();
        //testUpdate();
        //getById();
        //testFindAll();
        //testDelete();
        //getChambres();
        ////deleteLiens();
        //insertLiens();
        //updateLiens();
        testFindHistorique();
    }

    public static void deleteLiens(){
        Reservation r = new DAOReservationJDBC().getById(2);
        //Chambre c = new DAOChambreJDBC().getById(new Pair<>(1, 101));
        System.out.println(new DAOReservationJDBC().deleteLiens(r, null) ? "true": "false");
    }

    public static void getChambres() {
        List<Chambre> chambres = new DAOReservationJDBC().getChambres(1);
        for(Chambre c: chambres)
            System.out.println(c.toString());
    }

    public static void testInsert() {
        Client c = new Client();
        c.setNum(1);
        Reservation r = new Reservation(6, LocalDate.now(), LocalDate.now(), 15, "ATTENTR_CONFIRMATION2", 15, 0, c, null);
        r.setChambres(new DAOChambreJDBC().findAll());
        Reservation n = new DAOReservationJDBC().insert(r);
        System.out.println(n == null ? "null": n.toString());
    }


    public static void testUpdate() {
        Client c = new Client();
        c.setNum(1);
      Reservation r = new Reservation(1, LocalDate.now(), LocalDate.now(), 18, "ATTN", 0, 100, c, null);
        boolean n = new DAOReservationJDBC().update(r);
        System.out.println(n ? "true": false);
    }

    public static void getById() {
        Reservation r = new DAOReservationJDBC().getById(1);

        System.out.println(r);
        System.out.println(r.getChambres().size());
    }

    public static void testFindAll() {
        List<Reservation> reservations = new DAOReservationJDBC().findAll();

        for(Reservation r: reservations) {
            System.out.println(r.toString());
        }
    }

    public static void testDelete() {
        Reservation reservation = new DAOReservationJDBC().getById(3);

        boolean isDel = new DAOReservationJDBC().delete(reservation);
        System.out.println(isDel ? "true": "false");
    }

    public static void insertLiens() {
        Reservation reservation = new DAOReservationJDBC().getById(2);
        Chambre c = new DAOChambreJDBC().getById(new Pair<>(1, 101));

        System.out.println(new DAOReservationJDBC().insertLiens(reservation, c) ? "true": "false");
    }

    public static void updateLiens() {
        Reservation r= new DAOReservationJDBC().getById(2);
        r.setChambres(new ArrayList<>());
        r.getChambres().add(new DAOChambreJDBC().getById(new Pair<>(1, 301)));

        System.out.println(new DAOReservationJDBC().updateLiens(r) ? "true": "false");
    }

    public static void testFindHistorique() {
        List<Reservation> historique = new DAOReservationJDBC().findHistoriqueClient(34);
        System.out.println(historique);
    }

}
