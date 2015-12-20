package fr.pixcyan.android.raffennn.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

/**
 * Class qui permet la gestion des requêtes pour la class Mot
 * Created by raffennn on 09/12/2015.
 */
public class DAOMot extends DAOBase  {
    // Nom de la table
    public static final String TABLE_MOT = "MOT";

    // Table: MOT
    public static final String MOT = "mot";
    public static final String INDICE = "indice";
    public static final String NIVEAU = "niveau";

    // retourne une chaîne de caractères représentant une instruction SQL de création de la table mot
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_MOT + " (" +
                    MOT + " TEXT NOT NULL PRIMARY KEY, " +
                    INDICE + " Varchar(60)," +
                    NIVEAU + " int(2));";

    // retourne une chaîne de caractères représentant une instruction SQL de création de la table mot
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_MOT + ";";


    // Données pour la table
    private static final String[] DATA = new String[]{
            "'CRAIE', 'Si je vous dis \"tableau\" ?', 1",
            "'CETACE', 'Mamifère marin', 1",
            "'VOILE', 'Les mariées en portent...parfois', 1",
            "'TAMBOUR', 'Musique...\"bruyante\" ?', 1 ",
            "'CAMESCOPE', 'Pour enregistrer des vidéos', 1",
            "'TRIER', 'Action pour \"sauver\" la planète', 1",
            "'RECITER', 'Se fait pour un poème, ou une leçon', 1",
            "'MODELER', 'Pas d''indice', 2",
            "'SCHISME', 'Pas d''indice', 2",
            "'PIE', 'Pas d''indice', 2",
            "'TORDU', 'Pas d''indice', 2",
            "'ZIGZAG', 'Pas d''indice', 2",
            "'QUAI', 'Pas d''indice', 2",
            "'SEPT', 'Pas d''indice', 2",
            "'PASSERELLE', 'Pas d''indice', 2",
            "'FILET', 'Pas d''indice', 2",
            "'JEU', 'Pas d''indice', 3",
            "'PIED', 'Pas d''indice', 3",
            "'FOND', 'Pas d''indice', 3",
            "'VERT', 'Pas d''indice', 3",
            "'BRAS',  'Pas d''indice', 3",
            "'CINQ',  'Pas d''indice', 3",
            "'VIS', 'Pas d''indice', 3",
            "'FIL', 'Pas d''indice', 3",
            "'POKEMON', 'Pour le fun et le troll :p', 3"
    };

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_MOT + "("
                + MOT + ", "
                + INDICE + ", "
                + NIVEAU + ") VALUES ";

        String[] liste = new String[DATA.length];
        int i = 0;
        for (String Mot : DATA) {

            // Instruction SQL INSERT
            liste[i] = insertSQL + "(" + Mot + ")";
            i++;
        }

        //
        return liste;
    }

    public DAOMot(Context context) {
        super(context);
    }


    public long insert(Mot mot) {

        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(MOT, mot.getLibelle());
        values.put(INDICE, mot.getIndice());
        values.put(NIVEAU, mot.getNiveau());

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_MOT, null, values);
    }

    public int update(Mot mot) {

        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(MOT, mot.getLibelle());
        values.put(INDICE, mot.getIndice());
        values.put(NIVEAU, mot.getNiveau());

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        return getDB().update(TABLE_MOT, values, MOT + " = " + mot.getLibelle(), null);
    }

    public int removeByMot(String mot) {

        //Suppression d'une mot de la BD à partir de l'ID
        return getDB().delete(TABLE_MOT, MOT + " = " + mot, null);
    }

    public int remove(Mot mot) {

        return removeByMot(mot.getLibelle());
    }

    public List<Mot> selectAll() {

        //Récupère dans un Cursor les valeur correspondant à des enregistrements de mot contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_MOT, null);

        return cursorToListMot(cursor);
    }

    public Mot retrieveByMot(String mot) {
        //Récupère dans un Cursor les valeur correspondant à une mot contenu dans la BD à l'aide de son id
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_MOT + " WHERE " + MOT + "=?", new String[]{mot});
        return cursorToFirstMot(cursor);
    }

    public Mot getMotRandom(String niveau) {
        //Récupère dans un Cursor les valeur correspondant à un mot au hasard selon le niveau demandé
        Cursor cursor = getDB().rawQuery("SELECT * FROM "+ TABLE_MOT + " WHERE "+ NIVEAU +" = "+ niveau +" ORDER BY RANDOM() LIMIT 1", new String[]{});
        return cursorToFirstMot(cursor);
    }

    //Cette méthode permet de convertir un cursor en une liste de mots
    private List<Mot> cursorToListMot(Cursor cursor) {
        // Récupére l'index des champs
        int indexMot = cursor.getColumnIndex(MOT);
        int indexIndice = cursor.getColumnIndex(INDICE);
        int indexNiveau = cursor.getColumnIndex(NIVEAU);
        // Declaration et initialisation d'une liste de mots
        ArrayList<Mot> liste = new ArrayList<>();
        while (cursor.moveToNext()) {
            // Création d'un mot
            Mot mot = new Mot();
            mot.setLibelle(cursor.getString(indexMot));
            mot.setIndice(cursor.getString(indexIndice));
            mot.setNiveau(cursor.getInt(indexNiveau));

            // Ajout dans la liste
            liste.add(mot);
        }
        // Fermeture du cursor
        cursor.close();
        //
        return liste;
    }

    //Cette méthode permet de convertir un cursor en une mot
    private Mot cursorToFirstMot(Cursor cursor) {

        // Récupére l'index des champs
        int indexMot = cursor.getColumnIndex(MOT);
        int indexIndice = cursor.getColumnIndex(INDICE);
        int indexNiveau = cursor.getColumnIndex(NIVEAU);

        Mot mot = null;
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            // Création d'un mot
            mot = new Mot();
            mot.setLibelle(cursor.getString(indexMot));
            mot.setIndice(cursor.getString(indexIndice));
            mot.setNiveau(cursor.getInt(indexNiveau));

        }
        // Fermeture du cursor
        cursor.close();
        //
        return mot;
    }
}

