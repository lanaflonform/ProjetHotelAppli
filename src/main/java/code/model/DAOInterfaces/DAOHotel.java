package code.model.DAOInterfaces;

import code.Hotel;

/**
 * Created by Vincent on 03/05/2019.
 */
public interface DAOHotel extends DAO<Hotel, Integer> {

    public int getNbHotels();

}
