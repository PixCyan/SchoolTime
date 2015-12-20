package fr.pixcyan.android.raffennn;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import fr.pixcyan.android.raffennn.data.Compte;
import fr.pixcyan.android.raffennn.data.DAOCompte;
import fr.pixcyan.android.raffennn.data.DAOMot;
import fr.pixcyan.android.raffennn.data.Mot;
import java.util.HashSet;

public class PenduActivity extends ActionBarActivity {
    public final static int PERDU_REQUEST = 0;
    public final static int GAGNE_REQUEST = 1;
    public final static int PENDU_REQUEST = 2;
    public static final String LVL = "niveau";
    public static final String COMPTE = "compte";
    private String login;
    private DAOCompte daoCompte;
    private Compte compte;

    private Mot motAtrouver;
    private HashSet<Character> lettresEntrees;
    private char[] motActuel;
    private int pointPerdu;
    private String niveau;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendu);

        //récupération du login
        login = getIntent().getStringExtra(LoginActivity.COMPTE);
        this.daoCompte = new DAOCompte(this);

        final String login = getIntent().getStringExtra(LoginActivity.COMPTE);
        if (login != null) {
            this.daoCompte.open();
            this.compte = this.daoCompte.getCompte(login);
            this.daoCompte.close();
        } else {
            this.compte = null;
        }

        //choixNiveau dialog
        niveau = getIntent().getStringExtra(JeuxActivity.LVL);
        //

        this.pointPerdu = 0;
        this.nouveauMot();
        int tailleMot = this.tailleMot();
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        for(int i = 0; i < tailleMot; i++) {
            //text view
            TextView textV = new TextView(this);
            textV.setText("_");
            textV.setWidth(50);
            textV.setHeight(150);
            textV.setId(i);
            //textV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.addView(textV);
        }

        EditText editT = (EditText) findViewById(R.id.editText);
        editT.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        editT.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(1)
        });
    }

    public void valider(View view) {
        EditText editT = (EditText) findViewById(R.id.editText);
        this.comparerLettre(editT.getText().charAt(0));
        char[] mot = this.motActuel;
        for(int i = 0; i < this.tailleMot(); i++) {
            TextView textV = (TextView) findViewById(i);
            textV.setText(Character.toString(mot[i]));
        }
        editT.setText("");
        ImageView imgPendu = (ImageView) findViewById(R.id.imgPendu);
        int[] id = {R.drawable.pendu1, R.drawable.pendu2, R.drawable.pendu3,
                R.drawable.pendu4, R.drawable.pendu5,
                R.drawable.pendu6, R.drawable.pendu7,
                R.drawable.pendu8, R.drawable.pendu9,
                R.drawable.pendu10};
        imgPendu.setBackgroundResource(id[this.pointPerdu]);
        if(this.perduPendu()) {
            Toast.makeText(this, "PERDU ! Le mot était : "+ this.motAtrouver.getLibelle(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PerduPenduActivity.class);
            intent.putExtra(COMPTE, login);
            startActivityForResult(intent, PERDU_REQUEST);

        } else if(this.gagnerPendu()) {
            Intent intent = new Intent(this, GagnePenduActivity.class);
            this.score++;
            Toast.makeText(this, "GAGNE : "+ this.motAtrouver.getLibelle(), Toast.LENGTH_LONG).show();
            if (compte != null) {
                daoCompte.open();
                compte.setScore_pendu(compte.getScore_pendu() + score);
                daoCompte.update(this.compte);
                daoCompte.close();
                Toast.makeText(this, "Score = " + this.score, Toast.LENGTH_LONG).show();
            }
            intent.putExtra(COMPTE, login);
            startActivityForResult(intent, GAGNE_REQUEST);
        }
        TextView tv = (TextView) findViewById(R.id.lEntrees);
        String chaine = "";
        for(Character c: lettresEntrees) {
            chaine += c + " - ";
        }
        tv.setText("Lettre déjà entrées : " + chaine);
    }

    public void aide(View view) {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Trouver le mot en proposant des lettres. Attention, vous n'avez que 9 essais avant d'être \"pendu\". \n" +
                "Certains mots ont un indice, vous pouvez le consulter en appuyant sur l'icone ampoule.").setTitle("Aide");
        AlertDialog dialog = builder.create();
        dialog.show();
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

    public void afficherIndice(View view) {
        Toast.makeText(this, "Indice : "+ this.motAtrouver.getIndice(), Toast.LENGTH_LONG).show();
    }

    //ajout des méthodes de la class Pendu
    public void nouveauMot() {
        this.lettresEntrees = new HashSet<>();
        DAOMot MotDAO = new DAOMot(this);
        MotDAO.open();
        this.motAtrouver =  MotDAO.getMotRandom(this.niveau);
        this.motActuel = new char[this.tailleMot()];
        for(int i = 0; i < this.tailleMot(); i++) {
            this.motActuel[i] = '_';
        }
        MotDAO.close();
    }

    public boolean perduPendu() {
        boolean perdu = false;
        if(pointPerdu == 9) {
            perdu = true;
        } else {
            perdu = false;
        }
        return perdu;
    }

    public boolean gagnerPendu() {
        boolean gagner = false;
        if(new String(this.motActuel).equals(this.motAtrouver.getLibelle())) {
            gagner = true;
        } else {
            gagner = false;
        }
        return gagner;
    }

    public int tailleMot() {
        return this.motAtrouver.getLibelle().length();
    }

    public void comparerLettre(char lettreEntree) {
        char[] tabMot = this.motAtrouver.getLibelle().toCharArray();
        boolean lettrePrésente = false;
        for(int i = 0; i < tabMot.length; i++) {
            if(tabMot[i] == lettreEntree) {
                this.motActuel[i] = lettreEntree;
                this.lettresEntrees.add(lettreEntree);
                lettrePrésente = true;
            }
        }
        if(!lettrePrésente){
            this.lettresEntrees.add(lettreEntree);
            this.pointPerdu++;
        }
    }
}
