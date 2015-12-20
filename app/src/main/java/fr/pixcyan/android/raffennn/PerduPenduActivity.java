package fr.pixcyan.android.raffennn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import fr.pixcyan.android.raffennn.data.Compte;
import fr.pixcyan.android.raffennn.data.DAOCompte;

public class PerduPenduActivity extends ActionBarActivity {
    public static final String COMPTE = "compte";
    public final static int PENDU_REQUEST = 1;
    public static final String LVL = "niveau";
    private String login;
    private DAOCompte daoCompte;
    private Compte compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdu_pendu);

        login = getIntent().getStringExtra(LoginActivity.COMPTE);
        this.daoCompte = new DAOCompte(this);
        if (login != null) {
            this.daoCompte.open();
            this.compte = this.daoCompte.getCompte(login);
            this.daoCompte.close();
        } else {
            this.compte = null;
        }
    }

    public void rejouer(View view) {
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

    public void retourMenu(View view) {
        Intent intent = new Intent(this, JeuxActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(COMPTE, login);
        startActivity(intent);
    }

    public void quitter(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
