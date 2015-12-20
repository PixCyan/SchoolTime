package fr.pixcyan.android.raffennn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fr.pixcyan.android.raffennn.data.Compte;
import fr.pixcyan.android.raffennn.data.DAOCompte;

import java.util.Random;


public class MultiplicationActivity extends ActionBarActivity {
    public static final String FINAL_SCORE = "score";
    public static final String COMPTE = "compte";
    public final static int MULT_REQUEST = 0;
    private static final Random random = new Random();
    private static int nbCalc = 0;
    private String login;
    private int nb1;
    private int nb2;
    private Operation op;
    private int score;

    private DAOCompte daoCompte;
    private Compte compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);
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

        score = 0;

        miseAjour();

        /*//Création dynamique
        for(int i = 0; i < 3; i++) {
            TextView table = new TextView(this);
            table.setText(nb1 + " x " + nb2 + " = ");
            table.setWidth(50);
            table.setHeight(50);

            EditText rep = new EditText(this);
            rep.setText(" ? ");
            rep.setLayoutParams(params);
            rep.setTextSize(10);
            layout.addView(table);
            layout.addView(rep);
        }
        Button boutonAide = new Button(this);
        boutonAide.setText("Quitter");
        boutonAide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                aide();
            }
        });
        Button boutonMenu = new Button(this);
        boutonMenu.setText("Quitter");
        boutonMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                quitter();
            }
        });
        layout.addView(boutonAide);
        layout.addView(boutonMenu);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplication, menu);
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

    private void miseAjour() {
        nb1 = random.nextInt(15);
        nb2 = random.nextInt(15);
        op = new Multiplication(nb1, nb2);
        TextView view1 = (TextView) findViewById(R.id.chiffre1);
        view1.setText(nb1 + " x " + nb2 + " = ");
        EditText editText = (EditText) findViewById(R.id.rep);
        editText.setText("");
        TextView view2 = (TextView) findViewById(R.id.test1);
        view2.setText("");
    }

    public void valider(View view) {
        TextView view2 = (TextView) findViewById(R.id.rep);
        if(view2.getText().toString().matches("")) {
            Toast.makeText(this, "Vous n'avez pas rempli le champ", Toast.LENGTH_SHORT).show();
        } else {
            if (op.estJuste(Integer.parseInt(view2.getText().toString()))) {
                score++;
                Toast.makeText(this, "Bravo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect : " + op.calc(), Toast.LENGTH_SHORT).show();
            }
            nbCalc++;
            if (nbCalc != 10) {
                miseAjour();
            } else {
                if(compte != null) {
                    daoCompte.open();
                    compte.setScore_mult(Math.max(score, compte.getScore_mult()));
                    daoCompte.update(this.compte);
                    daoCompte.close();
                }
                nbCalc = 0;
                Intent intent = new Intent(this, ScoreActivity.class);
                intent.putExtra(FINAL_SCORE, score);
                intent.putExtra(COMPTE, login);
                score = 0;
                startActivityForResult(intent, MULT_REQUEST);
            }
        }
    }


    public void aide(View view) {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Donnez le résultat de la multiplication.").setTitle("Aide");
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
}
