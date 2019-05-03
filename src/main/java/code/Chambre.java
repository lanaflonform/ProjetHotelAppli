package code;

import java.util.HashSet;
import java.util.Set;

public class Chambre {

    private int numChambre;
    private Hotel hotel;
    private String etat;
    String type;
    private Set<Option> options = new HashSet<>();

    private int nombreLits;
    private float prix;

    public Chambre() {}

    public Chambre(int numChambre, Hotel hotel, String etat, String type, int nombreLits, float prix) {
        this.numChambre = numChambre;
        this.hotel = hotel;
        this.etat = etat;
        this.type = type;
        this.nombreLits = nombreLits;
        this.prix = prix;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNombreLits() {
        return nombreLits;
    }

    public void setNombreLits(int nombreLits) {
        this.nombreLits = nombreLits;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void addOption(Option opt) {
        options.add(opt);
        prix += opt.getPrix();
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "numChambre=" + numChambre +
                ", hotel=" + hotel +
                ", etat='" + etat + '\'' +
                ", type='" + type + '\'' +
                ", nombreLits=" + nombreLits +
                ", prix=" + prix +
                '}';
    }
}
