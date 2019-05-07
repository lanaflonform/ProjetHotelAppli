package Test;

import code.Admin;
import code.Hotel;
import code.TypeAcces;
import code.model.DAOJDBC.DAOAdminJDBC;
import code.model.DAOJDBC.DAOHotelJDBC;
import code.model.DAOJDBC.DAOTypeAccesJDBC;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import java.security.MessageDigest;

/**
 * Created by Vincent on 05/05/2019.
 */
public class AdminTest {

    DAOAdminJDBC daoAdminJDBC = new DAOAdminJDBC();
    List<TypeAcces> typesAcces = new DAOTypeAccesJDBC().findAll();

    @Test
    public void testGetTypesAcces() {
        Map<String, Boolean> droits = new HashMap<>();
        for (TypeAcces typeAcces : typesAcces) {
            droits.put(typeAcces.getTypeAcces(), false);
        };
        for (Map.Entry<String,Boolean> droit : droits.entrySet()) {
            System.out.println(droit.getKey() + " " + droit.getValue());
        }
        assertTrue(droits.size() == 4);
    }

    @Test
    public void testInsertAdmin() {
        String identifiant = "AdminCalanquesSupreme";
        String mdp = ("calanques");
        Map<String, Boolean> droits = new HashMap<>();
        int i = 0;
        for (TypeAcces typeAcces : typesAcces) {
            droits.put(typeAcces.getTypeAcces(), i%2 == 0);
            ++i;
        };
        List<Hotel> hotelsGeres = new ArrayList<>();
        hotelsGeres.addAll(new DAOHotelJDBC().findAll());
        Admin adminTest = new Admin();
        adminTest.setIdentifiant(identifiant);
        adminTest.setMdp(mdp);
        adminTest.setHotelsGeres(hotelsGeres);
        adminTest.setDroits(droits);
        Admin adminReturned = daoAdminJDBC.insert(adminTest);
        assertTrue(adminReturned == adminTest);
    }


    @Test
    public void testUpdateAdmin() {
        Admin admin = daoAdminJDBC.findByUsernameAndPassword("Georges", "mdptest");
        if (admin != null) {
            admin.setIdentifiant("GeorgesDeLaFacturation");
            assertTrue(daoAdminJDBC.update(admin));
        }
    }

    @Test
    public void testUpdateDroits() {
        Admin admin = daoAdminJDBC.findByUsernameAndPassword("AdminCalanquesSupreme", "calanques");
        if (admin != null) {
            Map<String, Boolean> droits = daoAdminJDBC.initDroits();
            for (TypeAcces typeAcces : typesAcces) {
                droits.put(typeAcces.getTypeAcces(), true);
            };
            admin.setDroits(droits);
            assertTrue(daoAdminJDBC.updateDroits(admin));
        }
    }

    @Test
    public void testUpdateHotelsGeres() {
        Admin admin = daoAdminJDBC.findByUsernameAndPassword("AdminCalanquesSupreme", "calanques");
        if (admin != null) {
            admin.getHotelsGeres().remove(1);
            daoAdminJDBC.updateHotelsGeres(admin);
        }
    }

    @Test
    public void testFindAll() {
        List<Admin> admins = daoAdminJDBC.findAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
        assertTrue(admins.size() == daoAdminJDBC.getNbAdmins());
    }

    @Test
    public void testGetById() {
        Admin admin = daoAdminJDBC.findByUsernameAndPassword("GeorgesDeLaFacturation", "mdptest");
        Admin adminTest = daoAdminJDBC.getById(admin.getNumAdmin());
        assertTrue(admin.equals(adminTest));
    }

    @Test
    public void testDeleteAdmin() {
        Admin admin = daoAdminJDBC.findByUsernameAndPassword("AdminCalanquesSupreme", "calanques");
        assertTrue(daoAdminJDBC.delete(admin));
    }

}