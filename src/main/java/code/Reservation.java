package code;

import java.time.LocalDate;
public class Reservation {
    private int num;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nbPersonnes;
    private String etat;
    private float reduction;

    private Client client;
    private Facture facture;
    private Chambre chambre;

    public Reservation(int num, LocalDate dateArrivee, LocalDate dateDepart, int nbPersonnes, String etat, float reduction, Client client, Facture facture, Chambre chambre) {
        this.num = num;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nbPersonnes = nbPersonnes;
        this.etat = etat;
        this.reduction = reduction;
        this.client = client;
        this.facture = facture;
        this.chambre = chambre;
    }

    public Reservation () {

    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
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

    public Facture getFacture() {
        return facture;
    }

    public Chambre getChambre() {
        return chambre;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "num=" + num +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", nbPersonnes=" + nbPersonnes +
                ", etat=" + etat +
                ", reduction=" + reduction + "\n" +
                ", client=" + client.toString() + "\n" +
                //", facture=" + facture.toString()+ "\n" +
                //", chambre=" + chambre.toString() +
                '}';
    }
}
