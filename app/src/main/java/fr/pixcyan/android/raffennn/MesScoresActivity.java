package fr.pixcyan.android.raffennn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import fr.pixcyan.android.raffennn.data.Compte;
import fr.pixcyan.android.raffennn.data.DAOCompte;


public class MesScoresActivity extends ActionBarActivity {
    public static final String COMPTE = "compte";
    private String login;
    private DAOCompte daoCompte;
    private Compte compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_scores);
        login = getIntent().getStringExtra(LoginActivity.COMPTE);
        this.daoCompte = new DAOCompte(this);
        if (login != null) {
            this.daoCompte.open();
            this.compte = this.daoCompte.getCompte(login);
            this.daoCompte.close();
        } else {
            this.compte = null;
        }

        if(compte != null) {
            TextView table1 = (TextView) findViewById(R.id.sMult);
            table1.setText("Multiplication :" + compte.getScore_mult());
            TextView table2 = (TextView) findViewById(R.id.sAdd);
            table2.setText("Addition :" + compte.getScore_add());
            TextView table3 = (TextView) findViewById(R.id.sArt);
            table3.setText("Art :" + compte.getScore_art());
            TextView table4 = (TextView) findViewById(R.id.sCap);
            table4.setText("Capitales :" + compte.getScore_capitales());

            TextView table5 = (TextView) findViewById(R.id.sSudo);
            if(this.compte.getScore_sudoku() == 20) {
                table5.setText("Sudoku réussi : Oui !");
            } else {
                table5.setText("Sudoku réussi : Non :s");
            }
            TextView table6 = (TextView) findViewById(R.id.sPendu);
            table6.setText("Nombre de mots trouvés : " + this.compte.getScore_pendu());

        } else {
            final Context context = this;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Aucun résultats trouvés. Vous n'êtes pas connecté ou une erreur s'est produite.").setTitle("Oups !");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mes_scores, menu);
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
