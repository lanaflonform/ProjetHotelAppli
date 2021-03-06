package code;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private int numReservation;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nbPersonnes;
    private String etat;
    private float prixTotal;
    private float reduction;

    private Client client;
    private List<Chambre> chambres = new ArrayList<>();
    private List<TypeService> services = new ArrayList<>();
    private Hotel hotel;

    public Reservation(int numReservation, LocalDate dateArrivee, LocalDate dateDepart, int nbPersonnes, String etat, float prixTotal, float reduction, Client client, List<Chambre> chambres, List<TypeService> services) {
        this.numReservation = numReservation;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nbPersonnes = nbPersonnes;
        this.etat = etat;
        this.prixTotal = prixTotal;
        this.reduction = reduction;
        this.client = client;
        this.chambres = chambres == null ? this.chambres: chambres;
        this.services = services == null ? this.services: services;
    }

    public Reservation () {

    }

    public List<TypeService> getServices() {
        return services;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public void setChambres(List<Chambre> chambres) {
        this.chambres = chambres;
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public void setNumReservation(int numReservation) {
        this.numReservation = numReservation;
    }

    public int getNumReservation() {
        return numReservation;
    }

    public LocalDate getDateArrivee() {
        return dateArrivee;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public String getEtat() {
        return etat;
    }

    public float getReduction() {
        return reduction;
    }

    public Client getClient() {
        return client;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setDateArrivee(LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setServices(List<TypeService> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "numReservation=" + numReservation +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", nbPersonnes=" + nbPersonnes +
                ", etat='" + etat  +
                ", prixTotal=" + prixTotal +
                ", reduction=" + reduction + "\n" +
                //", client=" + client.toString() + "\n" +
                //", chambres=" + chambres.toString() +
                '}';
    }
}
