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

    //Constructeur pour INSERT vers BD
    public Chambre(int numChambre, String etat, String type) {
        this.numChambre = numChambre;
        this.etat = etat;
        this.type = type;
    }

    //Constructeur pour UPDATE vers BD
    public Chambre(int numChambre, Hotel hotel, String etat, String type) {
        this.numChambre = numChambre;
        this.hotel = hotel;
        this.etat = etat;
        this.type = type;
    }

    //Constructeur pour récupération depuis BD
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chambre chambre = (Chambre) o;

        if (numChambre != chambre.numChambre) return false;
        if (nombreLits != chambre.nombreLits) return false;
        if (Float.compare(chambre.prix, prix) != 0) return false;
        if (hotel != null ? !hotel.equals(chambre.hotel) : chambre.hotel != null) return false;
        if (etat != null ? !etat.equals(chambre.etat) : chambre.etat != null) return false;
        if (type != null ? !type.equals(chambre.type) : chambre.type != null) return false;
        return options != null ? options.equals(chambre.options) : chambre.options == null;
    }
}
