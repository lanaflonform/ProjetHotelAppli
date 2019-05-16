package Test;

import code.Chambre;
import code.Client;
import code.Reservation;
import code.TypeService;
import code.model.DAOJDBC.DAOChambreJDBC;
import code.model.DAOJDBC.DAOClientJDBC;
import code.model.DAOJDBC.DAOReservationJDBC;
import code.model.DAOJDBC.DAOTypeServiceJDBC;
import javafx.util.Pair;

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
        //testFindHistorique();
        //etstGetServices();
        //insertService();
        deleteService();
    }

    public static void deleteLiens(){
        Reservation r = new DAOReservationJDBC().getById(2);
        //Chambre c = new DAOChambreJDBC().getById(new Pair<>(1, 101));
        System.out.println(new DAOReservationJDBC().deleteLiensChambre(r, null) ? "true": "false");
    }

    public static void getChambres() {
        List<Chambre> chambres = new DAOReservationJDBC().getChambres(1);
        for(Chambre c: chambres)
            System.out.println(c.toString());
    }

    public static void testInsert() {
        Client c = new Client();
        c.setNum(1);
        Reservation r = new Reservation(6, LocalDate.now(), LocalDate.now(), 15, "ATTENTR_CONFIRMATION2", 15, 0, c, null, null);
        r.setChambres(new DAOChambreJDBC().findAll());
        Reservation n = new DAOReservationJDBC().insert(r);
        System.out.println(n == null ? "null": n.toString());
    }

    public static void testUpdate() {
        Client c = new Client();
        c.setNum(1);
      Reservation r = new Reservation(1, LocalDate.now(), LocalDate.now(), 18, "ATTN", 0, 100, c, null, null);
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

        System.out.println(new DAOReservationJDBC().insertLiensChambre(reservation, c) ? "true": "false");
    }

    public static void updateLiens() {
        Reservation r= new DAOReservationJDBC().getById(2);
        r.setChambres(new ArrayList<>());
        r.getChambres().add(new DAOChambreJDBC().getById(new Pair<>(1, 301)));

        System.out.println(new DAOReservationJDBC().updateLiensChambre(r) ? "true": "false");
    }

    public static void testFindHistorique() {
        List<Reservation> historique = new DAOReservationJDBC().findHistoriqueClient(37);
        System.out.println(historique);
    }

    public static void etstGetServices() {
        for(TypeService t: new DAOReservationJDBC().getTypeServices(19)) {
            System.out.println(t.toString());
        }
    }

    public static void insertService() {/*
        TypeService s1 = new DAOTypeServiceJDBC().getById("Piscine");

        Reservation r = new DAOReservationJDBC().getById(2);

        System.out.println(new DAOReservationJDBC().insertLiensTypeService(r, s1) ? "true": "false");*/
        Reservation r = new DAOReservationJDBC().getById(18);
        r.setNumReservation(20);
        r.getServices().addAll(new DAOTypeServiceJDBC().findAll());

        System.out.println(r.toString());
        System.out.println(r.getServices().size());

        System.out.println(new DAOReservationJDBC().insert(r) != null ? "true": "false");

    }

    public static void deleteService() {/*
        TypeService s1 = new DAOTypeServiceJDBC().getById("Piscine");

        Reservation r = new DAOReservationJDBC().getById(18);

        System.out.println(new DAOReservationJDBC().deleteLiensTypeService(r, null) ? "true": "false");*/
        Reservation r = new DAOReservationJDBC().getById(18);
        System.out.println(new DAOReservationJDBC().delete(r) ? "true": "false");

    }
}
