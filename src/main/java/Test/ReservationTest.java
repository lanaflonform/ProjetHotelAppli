package Test;

import code.Reservation;
import code.model.DAOJDBC.DAOReservationJDBC;

import java.time.LocalDate;
import java.util.List;

public class ReservationTest {
    public static void main(String args[]) {
        //testInsert();
        //testUpdate();
        //getById();
        //testFindAll();
        testDelete();
    }

    public static void testInsert() {
        Reservation r = new Reservation(2, LocalDate.now(), LocalDate.now(), 15, "ATTENTR_CONFIRMATION2", 0, null, null, null);
        Reservation n = new DAOReservationJDBC().insert(r);
        System.out.println(n == null ? "null": n.toString());
    }


    public static void testUpdate() {
      Reservation r = new Reservation(1, LocalDate.now(), LocalDate.now(), 18, "ATTENdssdTR_CONFIRMATION", 0, null, null, null);
        boolean n = new DAOReservationJDBC().update(r);
        System.out.println(n ? "true": false);
    }

    public static void getById() {
        Reservation r = new DAOReservationJDBC().getById(1);
        System.out.println(r);
    }

    public static void testFindAll() {
        List<Reservation> reservations = new DAOReservationJDBC().FindAll();

        for(Reservation r: reservations) {
            System.out.println(r.toString());
        }
    }

    public static void testDelete() {
        Reservation reservation = new Reservation();
        reservation.setNumReservation(2);

        boolean isDel = new DAOReservationJDBC().delete(reservation);
        System.out.println(isDel ? "true": "false");
    }
}
