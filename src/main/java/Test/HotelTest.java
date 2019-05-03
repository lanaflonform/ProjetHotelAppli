package Test;

import code.Hotel;
import code.model.DAOJDBC.DAOHotelJDBC;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 03/05/2019.
 */
public class HotelTest {

    private DAOHotelJDBC daoHotelJDBC = new DAOHotelJDBC();

    @Test
    public void testInsertHotel() {

        Hotel hotel = new Hotel();
        hotel.setNom("Hotel des calanques");
        hotel.setAdresse("18 avenue des calanques");
        hotel.setVille("Marseille");
        hotel.setLatitude(45.5465f);
        hotel.setLongitude(12.54654f);

        Hotel hotelInsert = daoHotelJDBC.insert(hotel);
        assertTrue(hotel == hotelInsert);
    }

    @Test
    public void testUpdateHotel() {

        Hotel hotel = daoHotelJDBC.getById(7);
        hotel.setAdresse("28 avenue des calanques");
        assertTrue(daoHotelJDBC.update(hotel));
    }

    @Test
    public void testFindAllHotel() {
        List<Hotel> hotels = daoHotelJDBC.findAll();
        assertTrue(hotels.size() == daoHotelJDBC.getNbHotels());
    }

    @Test
    public void testDeleteHotel() {
        Hotel hotel = daoHotelJDBC.getById(7);
        assertTrue(daoHotelJDBC.delete(hotel));
    }

}
