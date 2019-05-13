package code.model.DAOInterfaces;

import code.Chambre;
import code.Reservation;

import java.util.List;

public interface DAOReservation extends DAO<Reservation, Integer> {
    public List<Chambre> getChambres(Integer integer);
    public boolean deleteLiens(Reservation reservation, Chambre chambre);
    public boolean insertLiens(Reservation reservation, Chambre chambre);
    public boolean updateLiens(Reservation reservation);
    public List<Reservation> findHistoriqueClient(Integer numClient);
}
