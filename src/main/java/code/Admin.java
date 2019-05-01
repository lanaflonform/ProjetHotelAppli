package code;

/**
 * Created by Vincent on 01/05/2019.
 */
public class Admin {

    private int numAdmin;
    private String identifiant;
    private String mdp;

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
