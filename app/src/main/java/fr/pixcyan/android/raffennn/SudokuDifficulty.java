package fr.pixcyan.android.raffennn;

import java.util.Random;

public enum SudokuDifficulty {

    EASY("Easy", 32, 45),
    MEDIUM("Medium", 46, 49),
    HARD("Hard", 49, 54);

    private final String label;
    private final int min;
    private final int max;

    private int nbHoles;

    private  SudokuDifficulty(String label, int min, int max){
        this.label = label;
        this.min = min;
        this.max = max;
    }

    public int holesToMake(){
        return this.min;
    }

    public int getMaximum(){
        return this.max;
    }

    public int computeHolesToMake() {
        Random r = new Random();
        this.nbHoles = r.nextInt(this.max - this.min) + this.min;
        return this.nbHoles;
    }

    public int getHolesToMake() {
        return this.nbHoles;
    }

    public String getLabel() {
        return label;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getNbHoles() {
        return nbHoles;
    }

    public void setNbHoles(int nbHoles) {
        this.nbHoles = nbHoles;
    }

    public String toString() {
        return this.label;
    }


}
