package Test;

import code.Chambre;
import code.Hotel;
import code.model.DAOJDBC.DAOChambreJDBC;
import code.model.DAOJDBC.DAOHotelJDBC;
import javafx.util.Pair;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 03/05/2019.
 */
public class ChambreTest {

    private DAOChambreJDBC daoChambreJDBC = new DAOChambreJDBC();

    @Test
    public void testInsertChambre() {

        Chambre chambre = new Chambre();
        //chambre.setHotel(new DAOHotelJDBC().getById(1));
        chambre.setNumChambre(213);
        //chambre.setEtat("OPEN");
        chambre.setType("Standard");

        Chambre chambreInsert = daoChambreJDBC.insert(chambre);
        assertTrue(chambre == chambreInsert);
    }

    @Test
    public void testUpdateChambre() {

        Chambre chambre = daoChambreJDBC.getById(new Pair<>(1,213));
        System.out.println(chambre);
        //chambre.setEtat("USED");
        assertTrue(daoChambreJDBC.update(chambre));
    }

    @Test
    public void testFindAllChambres() {
        List<Chambre> chambres = daoChambreJDBC.findAll();
        assertTrue(chambres.size() == daoChambreJDBC.getNbChambres());
        System.out.println(chambres);
    }

    @Test
    public void testDeleteChambre() {
        Chambre chambre = daoChambreJDBC.getById(new Pair<>(1,213));
        assertTrue(daoChambreJDBC.delete(chambre));
    }


    public static void main(String args[]) {
        Chambre chambre = new DAOChambreJDBC().getById(new Pair<>(12, 104));
        System.out.println(new DAOChambreJDBC().deleteHistorique(chambre) ?  "true": "false");
    }

}
