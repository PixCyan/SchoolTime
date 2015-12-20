package fr.pixcyan.android.raffennn.data;

/**
 * TODO
 *
 * @author PixCyan
 */
public class Compte {

    private String login;
    private String mdp;
    private int score_mult;
    private int score_add;
    private int score_art;
    private int score_capitales;
    private int score_sudoku;
    private int score_pendu;

    public  Compte() {

    }

    public Compte(String login, String mdp, int score_add, int score_mult, int score_art, int score_capitales, int score_sudoku, int score_pendu) {
        this.login = login;
        this.mdp = mdp;
        this.score_add = score_add;
        this.score_mult = score_mult;
        this.score_art = score_art;
        this.score_capitales = score_capitales;
        this.score_sudoku = score_sudoku;
        this.score_pendu = score_pendu;
    }

    public int getScore_sudoku() {
        return score_sudoku;
    }

    public void setScore_sudoku(int score_sudoku) {
        this.score_sudoku = score_sudoku;
    }

    public int getScore_pendu() {
        return score_pendu;
    }

    public void setScore_pendu(int score_pendu) {
        this.score_pendu = score_pendu;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public int getScore_mult() {
        return score_mult;
    }

    public int getScore_add() {
        return score_add;
    }

    public int getScore_art() {
        return score_art;
    }

    public int getScore_capitales() {
        return score_capitales;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setScore_mult(int score_mult) {
        this.score_mult = score_mult;
    }

    public void setScore_add(int score_add) {
        this.score_add = score_add;
    }

    public void setScore_art(int score_art) {
        this.score_art = score_art;
    }

    public void setScore_capitales(int score_capitales) {
        this.score_capitales = score_capitales;
    }



}
