package code.model.DAOInterfaces;

import code.Admin;

/**
 * Created by Vincent on 01/05/2019.
 */
public interface DAOAdmin extends DAO <Admin, Integer> {

    /*Ici on met les fonctions de récupération de données depuis la base spécifiques aux admins*/

    /**
     * A la connexion d'un admin, vérifier si celui-ci existe et renvoyer un objet Admin si oui
     * @param username le nom d'utilisateur saisi
     * @param password
     * @return l'objet Admin ou null s'il n'existe pas
     */
    public Admin findByUsernameAndPassword(String username, String password);

}
