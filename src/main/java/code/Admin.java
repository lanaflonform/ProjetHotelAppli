package code;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vincent on 01/05/2019.
 */
public class Admin {

    private int numAdmin;
    private String identifiant;
    private String mdp;
    private Map<String, Boolean> droits = new HashMap<>();

    public Admin() {}

    public Admin(int numAdmin, String identifiant, String mdp, Map<String, Boolean> droits) {
        this.numAdmin = numAdmin;
        this.identifiant = identifiant;
        this.mdp = mdp;
        this.droits = droits;
    }

    public int getNumAdmin() {
        return numAdmin;
    }

    public void setNumAdmin(int numAdmin) {
        this.numAdmin = numAdmin;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Map<String, Boolean> getDroits() {
        return droits;
    }

    public void setDroits(Map<String, Boolean> droits) {
        this.droits = droits;
    }

    @Override
    public String toString() {
        return "code.Admin{" +
                "numAdmin=" + numAdmin +
                ", identifiant='" + identifiant + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (numAdmin != admin.numAdmin) return false;
        if (!identifiant.equals(admin.identifiant)) return false;
        return mdp.equals(admin.mdp);
    }

    @Override
    public int hashCode() {
        int result = numAdmin;
        result = 31 * result + identifiant.hashCode();
        result = 31 * result + mdp.hashCode();
        return result;
    }
}
