package fr.pixcyan.android.raffennn.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * TODO
 *
 * @author PixCyan
 */
public class DBHelper extends SQLiteOpenHelper {
    // VERSION de la bdd, permet les mises à jour des tables et champs au lancement de l'application
    private static final int VERSION = 11;

    // NOM de la base
    private static final String DATABASE_NAME = "database_test_dut_as";

    // TAG pour le log
    private static final String TAG = "DBHelper";

    // Constructeur
    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Créer les tables
        db.execSQL(DAOQuestion.CREATE_TABLE);
        db.execSQL(DAOCompte.CREATE_TABLE);
        db.execSQL(DAOCapitale.CREATE_TABLE);
        db.execSQL(DAOMot.CREATE_TABLE);

        // Insérer les données
        for (String insertQ : DAOQuestion.getInsertSQL()) {
            db.execSQL(insertQ);
        }
        for (String insertC : DAOCompte.getInsertSQL()) {
            db.execSQL(insertC);
        }
        for (String insertCap : DAOCapitale.getInsertSQL()) {
            db.execSQL(insertCap);
        }
        for (String insertCap : DAOMot.getInsertSQL()) {
            db.execSQL(insertCap);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Log
        Log.w(TAG, "UPGRADING DATABASE FROM VERSION " + oldVersion
                + " TO "
                + newVersion + ", WHICH WILL DESTROY ALL OLD DATA !");

        // DROP
        db.execSQL(DAOCompte.DROP_TABLE);
        db.execSQL(DAOQuestion.DROP_TABLE);
        db.execSQL(DAOCapitale.DROP_TABLE);
        db.execSQL(DAOMot.DROP_TABLE);

        // Relancer la création
        onCreate(db);
    }
}
