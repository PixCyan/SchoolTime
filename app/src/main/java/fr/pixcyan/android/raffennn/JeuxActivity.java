package fr.pixcyan.android.raffennn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class JeuxActivity extends ActionBarActivity {
    public final static int JEUX_REQUEST = 0;
    public final static int EXERCICES_REQUEST = 1;
    public final static int CULTURE_REQUEST = 2;
    public static final String COMPTE = "compte";
    public final static int SUDOKU_REQUEST =3;
    public final static int PENDU_REQUEST = 4;
    public static final String LVL = "niveau";
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeux);
        login = getIntent().getStringExtra(LoginActivity.COMPTE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jeux, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void exoMaths(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, ExercicesMathsActivity.class);
        intent.putExtra(COMPTE, login);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, EXERCICES_REQUEST);
    }

    public void exoCulture(View view) {
        // Création d'une intention
        Intent intent = new Intent(this, ExosCultureActivity.class);
        intent.putExtra(COMPTE, login);
        // Lancement de la demande de changement d'activité
        startActivityForResult(intent, CULTURE_REQUEST);
    }

    public void mesScores(View view) {
        Intent intent = new Intent(this, MesScoresActivity.class);
        intent.putExtra(COMPTE, login);
        startActivityForResult(intent, CULTURE_REQUEST);
    }

    public void quitter(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //Ajout Sudoku / Pendu
    public void lancerSudoku(View view) {
        final CharSequence[] items = {"1 : facile", "2 : intermédiaire", "3 : difficile"};
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choisissez une niveau : ");
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ListView lv = ((AlertDialog) dialog).getListView();
                lv.setTag(new Integer(which));
                choixLvlSudoku(lv.getTag().toString());
            }
        });
        dialogBuilder.create().show();

    }

    private void choixLvlSudoku(String valeur) {
        Intent intent = new Intent(this, SudokuActivity.class);
        int choix = Integer.parseInt(valeur)+1;
        intent.putExtra(LVL, Integer.toString(choix));
        intent.putExtra(COMPTE, login);
        startActivityForResult(intent, SUDOKU_REQUEST);
    }

    public void lancerPendu(View view) {
        final CharSequence[] items = {"1 : facile", "2 : intermédiaire", "3 : difficile"};
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choisissez une niveau : ");
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ListView lv = ((AlertDialog) dialog).getListView();
                lv.setTag(new Integer(which));
                choixLvlPendu(lv.getTag().toString());
            }
        });
        dialogBuilder.create().show();
    }

    private void choixLvlPendu(String valeur) {
        Intent intent = new Intent(this, PenduActivity.class);
        int choix = Integer.parseInt(valeur)+1;
        intent.putExtra(LVL, Integer.toString(choix));
        intent.putExtra(COMPTE, login);
        startActivityForResult(intent, PENDU_REQUEST);
    }

}
