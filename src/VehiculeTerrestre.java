import java.io.Serializable;

public abstract class VehiculeTerrestre extends Vehicule{

    private int km;
    private float puissance;

    public VehiculeTerrestre()
    {
        super();
    }

    public VehiculeTerrestre(String modele, String marque, float prixJournalier, float vitesseMax, String etat, int km, float puissance) {
        super(modele, marque,prixJournalier, vitesseMax,  etat);
        this.km = km;
        this.puissance = puissance;
    }

    protected abstract String printType();

    public void setKm(int km) {
        this.km = km;
    }

    public void setPuissance(float puissance) {
        this.puissance = puissance;
    }

    public int getKm() {
        return km;
    }

    public float getPuissance() {
        return puissance;
    }
}
