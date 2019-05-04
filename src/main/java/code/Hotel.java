package code;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vincent on 03/05/2019.
 */
public class Hotel {

    private int numHotel;
    private String nom;
    private String ville;
    private  String adresse;
    private float latitude;
    private float longitude;
    private Set<TypeService> services = new HashSet<>();

    public Hotel() {}

    public Hotel(int numHotel, String nom, String ville, String adresse, float latitude, float longitude, Set<TypeService> services) {
        this.numHotel = numHotel;
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.services = services;
    }

    public int getNumHotel() {
        return numHotel;
    }

    public void setNumHotel(int numHotel) {
        this.numHotel = numHotel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Set<TypeService> getServices() {
        return services;
    }

    public void setServices(Set<TypeService> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "numHotel=" + numHotel +
                ", nom='" + nom + '\'' +
                ", ville='" + ville + '\'' +
                ", adresse='" + adresse + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", services=" + services +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (numHotel != hotel.numHotel) return false;
        if (Float.compare(hotel.latitude, latitude) != 0) return false;
        if (Float.compare(hotel.longitude, longitude) != 0) return false;
        if (!nom.equals(hotel.nom)) return false;
        if (!ville.equals(hotel.ville)) return false;
        return adresse.equals(hotel.adresse);
    }

    @Override
    public int hashCode() {
        int result = numHotel;
        result = 31 * result + nom.hashCode();
        result = 31 * result + ville.hashCode();
        result = 31 * result + adresse.hashCode();
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        return result;
    }
}
