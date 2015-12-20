package fr.pixcyan.android.raffennn;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.pixcyan.android.raffennn.data.Compte;
import fr.pixcyan.android.raffennn.data.DAOCompte;


public class SudokuActivity extends ActionBarActivity {
    public final static int SUDO_REQUEST = 0;
    public static final String LVL = "niveau";
    public static final String COMPTE = "compte";
    private String login;
    private DAOCompte daoCompte;
    private Compte compte;
    private int score;

    /**
     * Convertit un indice utilisé par la vue en un indice utilisé par le modèle et inversement.
     *
     * @param k indice à convertir
     * @return indice convertit
     */
    public static int convertIndex(int k) {
        int xGrid = k % 9 / 3;
        int yGrid = k / 27;
        int numGrid = yGrid * 3 + xGrid;
        int numCell = (k - (yGrid * 27 + xGrid * 3)) / 3 + k % 3;
        return numGrid * 9 + numCell; // n
    }

    /**
     * Convertit la coordonnée d'une case d'un sudoku représenté par un tableauu 1D en coordonnées
     * équivalentes dans un tableau 2D.
     *
     * @param n coordonnée à convertir
     * @return coordonnées converties
     */
    public static int[] indexToCoord(int n) {
        return new int[]{n / 9, n % 9};
    }

    /**
     * Convertit les coordonnées d'une case d'un sudoku représenté par un tableauu 2D en une
     * coordonnée équivalente dans un tableau 1D.
     *
     * @param i coordonnée à convertir
     * @param j coordonnée à convertir
     * @return coordonnée convertie
     */
    public static int coordToIndex(int i, int j) {
        return i * 9 + j;
    }

    private String niveau;
    SudokuGrid9x9 sudokuGame;
    private SudokuDifficulty difficulte;
    private int[] sudokuTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

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
        switch (niveau) {
            case "1":
                this.difficulte = SudokuDifficulty.EASY;
                break;
            case "2":
                this.difficulte = SudokuDifficulty.MEDIUM;
                break;
            case "3":
                this.difficulte = SudokuDifficulty.HARD;
                break;
            default:
                break;
        }
        //Récupération du GridLayout
        GridView gridview = (GridView) findViewById(R.id.grid);
        this.sudokuGame = new SudokuGrid9x9(difficulte);

        int[] tab = new int[81];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[convertIndex(coordToIndex(i, j))] = Integer.parseInt(this.sudokuGame.getValueAt(i, j));
            }
        }
        this.sudokuTab = tab;
        gridview.setAdapter(new GrilleAdapter());
    }

    public void valider(View view) {
        List<SudokuError> errors = this.sudokuGame.checkGrid();
        if(errors.isEmpty()) {
            Toast.makeText(SudokuActivity.this, "Sudoku VALIDE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SudokuActivity.this, "Sudoku FAUX", Toast.LENGTH_SHORT).show();
        }

        //Si la personne est connecté et que le sudoku est juste (ne contient pas d'erreur)
        //Ajout du score au compte
        if (compte != null && errors.isEmpty()) {
            this.score = 20;
            daoCompte.open();
            compte.setScore_sudoku(Math.max(score, compte.getScore_sudoku()));
            daoCompte.update(this.compte);
            daoCompte.close();
        }
    }

    public void aide(View view) {
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Remplissez les cases de façon à ce qu'il n'y ai aucun chiffre en double à la fois dans une ligne, une colonne et une sous-grille.").setTitle("Aide");
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

    private boolean emptyCellInTab() {
        boolean res = false;
        for(int i = 0; i < sudokuTab.length; i++) {
            if(sudokuTab[i] == 0) {
                res = true;
                break;
            }
        }
        return res;
    }

    private class SousGrilleAdapter extends BaseAdapter {
        private final int positionDepart;

        public SousGrilleAdapter(int positionDepart) {
            this.positionDepart = positionDepart;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return sudokuTab[positionDepart + position];
        }

        @Override
        public long getItemId(int position) {
            return this.positionDepart + position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //création d'une classe anonyme
            final TextView res = new TextView(SudokuActivity.this) {

                @Override
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    //forcer la vue à être carrée
                    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                }

            };
            if(getItem(position).toString().equals("0")) {
                res.setText("");
                res.setBackgroundColor(Color.WHITE);
                res.setClickable(true);
                res.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(res.getContext(), getItem(position).toString(), Toast.LENGTH_SHORT).show();
                        final Dialog dialogue = new Dialog(SudokuActivity.this);
                        dialogue.setTitle("Choisissez un nombre : ");
                        dialogue.setContentView(R.layout.number_picker);
                        Button bouton = (Button) dialogue.findViewById(R.id.bouton);

                        final NumberPicker np = (NumberPicker) dialogue.findViewById(R.id.np);
                        np.setMaxValue(9);
                        np.setMinValue(1);
                        bouton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(SudokuActivity.this, Integer.toString(np.getValue()), Toast.LENGTH_SHORT).show();
                                res.setText(Integer.toString(np.getValue()));
                                dialogue.dismiss();
                                int[] vtg = indexToCoord(convertIndex(positionDepart + position));
                                System.out.println("vtg == " + vtg[0] + "  " + vtg[1]);
                                System.out.println("Solution = " + sudokuGame.getSolutionAt(vtg[0], vtg[1]) );
                                if(!(sudokuGame.getSolutionAt(vtg[0], vtg[1]).equals(res.getText().toString()))) {
                                    Toast.makeText(SudokuActivity.this, "FAUX", Toast.LENGTH_SHORT).show();
                                } else {
                                    sudokuGame.setValueAt(vtg[0], vtg[1], Integer.parseInt(res.getText().toString()));
                                }

                            }
                        });
                        dialogue.show();
                    }
                });
            } else {
                res.setBackgroundColor(0xFFC0C0C0);
                res.setText(this.getItem(position).toString());
            }
            res.setGravity(Gravity.CENTER);
            //ignore le padding du texte :
            res.setIncludeFontPadding(false);
            res.setSingleLine(true);
            res.setPadding(0, 0, 0, 0);
            return res;
        }
    }

    //Adapter pour GridView principale
    private class GrilleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridView sousGrille = new GridView(SudokuActivity.this) {

                @Override
                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    //forcer la vue à être carrée
                    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                }

            };

            float densite = getResources().getDisplayMetrics().density;
            int dpToPixel = (int) (1 * densite);
            sousGrille.setAdapter(new SousGrilleAdapter(position * 9));
            sousGrille.setNumColumns(3);
            sousGrille.setHorizontalSpacing(dpToPixel);
            sousGrille.setVerticalSpacing(dpToPixel);

            return sousGrille;
        }
    }
}
