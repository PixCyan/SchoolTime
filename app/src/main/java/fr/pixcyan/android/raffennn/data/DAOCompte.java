package fr.pixcyan.android.raffennn.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author PixCyan
 */
public class DAOCompte extends DAOBase {

    // Nom de la table
    public static final String TABLE_COMPTE = "COMPTE";

    // Table: QUESTION
    public static final String LOGIN = "login";
    public static final String MDP = "mot_de_passe";
    public static final String SCORE_MATHS_MULT = "score_multiplication";
    public static final String SCORE_MATHS_ADD = "score_addition";
    public static final String SCORE_CULT_ART = "score_art";
    public static final String SCORE_CULT_CAPITALES = "score_capitales";
    public static final String SCORE_SUDOKU = "score_sudoku";
    public static final String SCORE_PENDU = "score_pendu";

    // retourne une chaîne de caractères représentant une instruction SQL de création de la table compte
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_COMPTE + "(" +
                    LOGIN + " VARCHAR(20) PRIMARY KEY, " +
                    MDP + " VARCHAR(15), " +
                    SCORE_MATHS_MULT + " NUMERIC(2), " +
                    SCORE_MATHS_ADD + " NUMERIC(2), " +
                    SCORE_CULT_ART + " NUMERIC(2), " +
                    SCORE_CULT_CAPITALES + " NUMERIC(2), " +
                    SCORE_SUDOKU + " NUMERIC(2), " +
                    SCORE_PENDU + " NUMERIC(2)); ";

    // retourne une chaîne de caractères représentant une instruction SQL de création de la table compte
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_COMPTE + ";";

    private static final String[] DATA = new String[]{
            "'admin', 'admin', '0', '0', '0', '0', '0', '0'",
            "'test', 'test', '0', '0', '0', '0', '0', '0'"};

    //Constructeur
    public DAOCompte(Context context) {
        super(context);
    }

    // retourne une liste de chaînes de caractères représentant les instructions SQL d'insertion de données dans la table
    public static String[] getInsertSQL() {
        String insertSQL = "INSERT INTO " + TABLE_COMPTE + "("
                + LOGIN + ", "
                + MDP + ", "
                + SCORE_MATHS_MULT + ", "
                + SCORE_MATHS_ADD + ", "
                + SCORE_CULT_ART + ", "
                + SCORE_CULT_CAPITALES + ", "
                + SCORE_SUDOKU + ", "
                + SCORE_PENDU +") VALUES ";

        String[] liste = new String[DATA.length];
        int i = 0;
        for (String compte : DATA) {
            // Instruction SQL INSERT
            liste[i] = insertSQL + "(" + compte + ")";
            i++;
        }
        return liste;
    }

    public long insert(Compte compte) {

        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(LOGIN, compte.getLogin());
        values.put(MDP, compte.getMdp());
        values.put(SCORE_MATHS_MULT, compte.getScore_mult());
        values.put(SCORE_MATHS_ADD, compte.getScore_add());
        values.put(SCORE_CULT_ART, compte.getScore_art());
        values.put(SCORE_CULT_CAPITALES, compte.getScore_capitales());
        values.put(SCORE_SUDOKU, compte.getScore_sudoku());
        values.put(SCORE_PENDU, compte.getScore_pendu());

        // Insertion de l'objet dans la BD via le ContentValues
        return getDB().insert(TABLE_COMPTE, null, values);
    }

    public int update(Compte compte) {

        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        // Ajout clé/valeur : colonne/valeur
        values.put(LOGIN, compte.getLogin());
        values.put(MDP, compte.getMdp());
        values.put(SCORE_MATHS_MULT, compte.getScore_mult());
        values.put(SCORE_MATHS_ADD, compte.getScore_add());
        values.put(SCORE_CULT_ART, compte.getScore_art());
        values.put(SCORE_CULT_CAPITALES, compte.getScore_capitales());
        values.put(SCORE_SUDOKU, compte.getScore_sudoku());
        values.put(SCORE_PENDU, compte.getScore_pendu());

        // Insertion de l'objet dans la BD via le ContentValues et l'identifiant
        return getDB().update(TABLE_COMPTE, values, LOGIN + " = ?", new String[]{compte.getLogin()});
    }

    public int removeByLogin(String login) {
        //Suppression d'une question de la BD à partir du login
        return getDB().delete(TABLE_COMPTE, LOGIN + " = " + login, null);
    }

    public int remove(Compte compte) {

        return removeByLogin(compte.getLogin());
    }

    public List<Compte> selectAll() {

        //Récupère dans un Cursor les valeur correspondant à des enregistrements de question contenu dans la BD
        Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_COMPTE, null);

        return cursorToListCompte(cursor);
    }

    public Compte getCompte(String login) {
        final Cursor cursor = getDB().rawQuery("SELECT * FROM " + TABLE_COMPTE + " WHERE " + LOGIN + " = ?", new String[]{login});
        return this.cursorToFirstCompte(cursor);
    }

    public boolean compteExiste(String login) {
        final SQLiteStatement req = getDB().compileStatement("SELECT login FROM " + TABLE_COMPTE + " WHERE " + LOGIN + " = ?");
        req.bindString(1, login);
        try {
            req.simpleQueryForString();
            return true;
        } catch (final SQLiteDoneException e) {
            return false;
        }
    }


    //Cette méthode permet de convertir un cursor en une liste de questions
    private List<Compte> cursorToListCompte(Cursor cursor) {

        // Récupére l'index des champs
        int indexLogin = cursor.getColumnIndex(LOGIN);
        int indexMDP = cursor.getColumnIndex(MDP);
        int indexScoreMult = cursor.getColumnIndex(SCORE_MATHS_MULT);
        int indexScoreAdd = cursor.getColumnIndex(SCORE_MATHS_ADD);
        int indexScoreArt = cursor.getColumnIndex(SCORE_CULT_ART);
        int indexScoreCap = cursor.getColumnIndex(SCORE_CULT_CAPITALES);
        int indexScoreSudo = cursor.getColumnIndex(SCORE_SUDOKU);
        int indexScorePendu = cursor.getColumnIndex(SCORE_PENDU);


        // Declaration et initialisation d'une liste de compte
        ArrayList<Compte> liste = new ArrayList<>();

        while (cursor.moveToNext()) {

            // Création d'un compte
            Compte compte = new Compte();
            compte.setLogin(cursor.getString(indexLogin));
            compte.setMdp(cursor.getString(indexMDP));
            compte.setScore_mult(cursor.getInt(indexScoreMult));
            compte.setScore_add(cursor.getInt(indexScoreAdd));
            compte.setScore_art(cursor.getInt(indexScoreArt));
            compte.setScore_capitales(cursor.getInt(indexScoreCap));
            compte.setScore_sudoku(cursor.getInt(indexScoreSudo));
            compte.setScore_pendu(cursor.getInt(indexScorePendu));

            // Ajout dans la liste
            liste.add(compte);
        }
        // Fermeture du cursor
        cursor.close();
        return liste;
    }

    //Cette méthode permet de convertir un cursor en un compte
    private Compte cursorToFirstCompte(Cursor cursor) {

        // Récupére l'index des champs
        int indexLogin = cursor.getColumnIndex(LOGIN);
        int indexMDP = cursor.getColumnIndex(MDP);
        int indexScoreMult = cursor.getColumnIndex(SCORE_MATHS_MULT);
        int indexScoreAdd = cursor.getColumnIndex(SCORE_MATHS_ADD);
        int indexScoreArt = cursor.getColumnIndex(SCORE_CULT_ART);
        int indexScoreCap = cursor.getColumnIndex(SCORE_CULT_CAPITALES);
        int indexScoreSudo = cursor.getColumnIndex(SCORE_SUDOKU);
        int indexScorePendu = cursor.getColumnIndex(SCORE_PENDU);

        // Declaration d'une compte
        Compte compte = null;

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            // Création d'un compte
            compte = new Compte();
            compte.setLogin(cursor.getString(indexLogin));
            compte.setMdp(cursor.getString(indexMDP));
            compte.setScore_mult(cursor.getInt(indexScoreMult));
            compte.setScore_add(cursor.getInt(indexScoreAdd));
            compte.setScore_art(cursor.getInt(indexScoreArt));
            compte.setScore_capitales(cursor.getInt(indexScoreCap));
            compte.setScore_sudoku(cursor.getInt(indexScoreSudo));
            compte.setScore_pendu(cursor.getInt(indexScorePendu));

        }

        // Fermeture du cursor
        cursor.close();
        return compte;
    }



}
