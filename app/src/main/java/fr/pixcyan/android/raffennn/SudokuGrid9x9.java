package fr.pixcyan.android.raffennn;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * Class SudokuGrid.
 *
 * <p>Generates a random Sudoku 9x9 grid based on a difficulty level,
 * as defined by <code>SudokuDifficulty</code> enum.</p>
 * @see SudokuDifficulty
 * @see ISudokuGrid
 * @author: Camille Persson
 *
 */
public class SudokuGrid9x9 implements ISudokuGrid {

    protected static final int MAX_PERMUTATION = 10;
    protected static final int [][] BASE_GRID = {
            // A B C D E F G H I
            {3,2,9,6,5,7,8,4,1}, // A
            {7,4,5,8,3,1,2,9,6}, // B
            {6,1,8,2,4,9,3,7,5}, // C
            {1,9,3,4,6,8,5,2,7}, // D
            {2,7,6,1,9,5,4,8,3}, // E
            {8,5,4,3,7,2,6,1,9}, // F
            {4,3,2,7,1,6,9,5,8}, // G
            {5,8,7,9,2,3,1,6,4}, // H
            {9,6,1,5,8,4,7,3,2}};// I

    protected ISudokuCell [][] grid;
    protected ISudokuCell [][] solution;
    protected static Random r = new Random(System.currentTimeMillis());

    /**
     * Constructor.
     * @param difficulty a <code>SudokuDifficulty</code> instance
     * representing the difficulty level.
     * @see SudokuDifficulty
     */
    public SudokuGrid9x9(SudokuDifficulty difficulty) {
        this.generateGrid();
        this.makeHoles(difficulty.computeHolesToMake());
    }

    /* **** Protected Methods **** */
    protected void generateGrid() {
        this.initGridFromBaseGrid();

        int nbPermut =  r.nextInt(MAX_PERMUTATION);
        for(int i = 0; i < nbPermut; i++)
            doColumnPermutation();

        nbPermut =  r.nextInt(MAX_PERMUTATION);
        for(int i = 0; i < nbPermut; i++)
            doRowPermutation();
    }

    protected void initGridFromBaseGrid() {
        this.grid = new ISudokuCell[BASE_GRID.length][BASE_GRID[0].length];
        this.solution = new ISudokuCell[BASE_GRID.length][BASE_GRID[0].length];
        for(int i = 0 ; i < this.grid.length ; i++)
            for(int j = 0 ; j < this.grid[0].length ; j++) {
                this.grid[i][j] = new SudokuCell(BASE_GRID[i][j]);
                this.solution[i][j] = new SudokuCell(BASE_GRID[i][j]);
            }
    }

    protected void doColumnPermutation() {
        int col1 = r.nextInt(3);
        int col2 = (col1 + 1) % 3;

        int groupToMove = r.nextInt(3);
        col1 += groupToMove;
        col2 += groupToMove;

        int tmp;

        for(int i = 0 ; i < this.grid.length ; i++) {
            this.grid[i][col1].permuteValueWith(this.grid[i][col2]);
            this.solution[i][col1].permuteValueWith(this.solution[i][col2]);
        }
    }

    protected void doRowPermutation() {
        int row1 = r.nextInt(3);
        int row2 = (row1 + 1) % 3;

        int groupToMove = r.nextInt(3);
        row1 += groupToMove;
        row2 += groupToMove;

        int tmp;

        for(int i = 0 ; i < this.grid.length ; i++) {
            this.grid[row1][i].permuteValueWith(this.grid[row2][i]);
            this.solution[row1][i].permuteValueWith(this.solution[row2][i]);
        }
    }

    protected void makeHoles(int nbOfHoles) {
        List<ISudokuCell> cells = new LinkedList<ISudokuCell>();
        for(int i = 0; i<this.grid.length; i++)
            for(int j = 0 ; j < this.grid[0].length ; j++)
                cells.add(this.grid[i][j]);
        Collections.shuffle(cells);
        for(int i = 0 ; i < nbOfHoles; i++)
            cells.get(i).setInitialValue(ISudokuCell.HOLE_VALUE);
    }

    protected List<SudokuError> checkCell(int x, int y) {
        List<SudokuError> error = new LinkedList<SudokuError>();
        if(this.grid[x][y].getValue() != this.solution[x][y].getValue())
            error.add(new SudokuError(x,y, this.grid[x][y]));
        return error;
    }

    /* **** PUBLIC METHODS **** */

    /**
     * @param x     the cell's row. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @param y     the cell's column. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @return the value of the cell at row x and column y. Empty cell is denoted by "0".
     */
    public String getValueAt(int x, int y) {
        return Integer.toString(this.grid[x][y].getValue());
    }


    /**
     * @param x     the cell's row. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @param y     the cell's column. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @return the value of the cell at row x and column y. Empty cell is denoted by "0".
     */
    public String getSolutionAt(int x, int y) {
        return Integer.toString(this.solution[x][y].getValue());
    }

    /**
     * @param x     the cell's row. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @param y     the cell's column. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @param value the value of the cell at row x and column y. For
     *              empty cell use <code>ISudokuCell.HOLE_VALUE</code>.
     * @see ISudokuGrid#setValueAt
     */
    public void setValueAt(int x, int y, int value) {
        this.grid[x][y].setValue(value);
    }

    /**
     * @see ISudokuGrid#toString
     */
    public String toString() {
        StringBuffer bf = new StringBuffer();
        for(int i = 0; i<this.grid.length; i++) {
            for(int j = 0 ; j < this.grid[0].length ; j++)
                bf.append(this.grid[i][j].toString());
            bf.append("\n");
        }
        return bf.toString();
    }

    /**
     * @see ISudokuGrid#checkGrid
     */
    public List<SudokuError> checkGrid() {
        List<SudokuError> errors = new LinkedList<SudokuError>();
        for(int i = 0; i<this.grid.length; i++)
            for(int j = 0 ; j < this.grid[0].length ; j++)
                errors.addAll(this.checkCell(i,j));
        return errors;
    }
}
