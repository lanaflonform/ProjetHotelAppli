package Test;

import code.Hotel;
import code.TypeService;
import code.model.DAOJDBC.DAOHotelJDBC;

import code.model.DAOJDBC.DAOTypeServiceJDBC;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<TypeService> services = new HashSet<>();
        services.add(new DAOTypeServiceJDBC().getById("Restaurant"));
        services.add(new DAOTypeServiceJDBC().getById("Piscine"));
        hotel.setServices(services);

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
    public void testUpdateServicesHotel() {
        Hotel hotel = daoHotelJDBC.getById(11);
        System.out.println(hotel);
        Set<TypeService> services = new HashSet<>();
        services.add(new DAOTypeServiceJDBC().getById("Petit-dejeuner"));
        services.add(new DAOTypeServiceJDBC().getById("Restaurant"));
        hotel.setServices(services);
        daoHotelJDBC.updateServices(hotel);
    }

    @Test
    public void testFindAllHotel() {
        List<Hotel> hotels = daoHotelJDBC.findAll();
        for(Hotel hotel : hotels) System.out.println(hotel);
        assertTrue(hotels.size() == daoHotelJDBC.getNbHotels());
    }

    @Test
    public void testDeleteHotel() {
        Hotel hotel = daoHotelJDBC.getById(9);
        assertTrue(daoHotelJDBC.delete(hotel));
    }

}
