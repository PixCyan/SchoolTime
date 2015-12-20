package fr.pixcyan.android.raffennn.data;

/**
 * Created by raffennn on 09/12/2015.
 */
public class Mot {
    private String libelle;
    private String indice;
    private int niveau;

    public Mot(String libelle, String indice, int niveau) {
        this.libelle = libelle;
        this.indice = indice;
        this.niveau = niveau;
    }

    public Mot() {
        //
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
}
