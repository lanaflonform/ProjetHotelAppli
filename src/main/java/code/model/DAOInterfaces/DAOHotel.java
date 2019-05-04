package code.model.DAOInterfaces;

import code.Hotel;
import code.TypeService;

import java.util.Set;

/**
 * Created by Vincent on 03/05/2019.
 */
public interface DAOHotel extends DAO<Hotel, Integer> {

    public int getNbHotels();
    public boolean updateServices(Hotel obj);
    public void insertServices(int numHotel, Set<TypeService> services);

}
