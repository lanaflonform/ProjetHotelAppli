package code.model.DAOInterfaces;

import code.Chambre;
import code.Hotel;
import javafx.util.Pair;

import java.util.Set;


/**
 * Created by Vincent on 03/05/2019.
 */

public interface DAOChambre extends DAO<Chambre, Pair<Integer, Integer>> {

    public int getNbChambres();
    public Set<String> getTypeChambres();

}
