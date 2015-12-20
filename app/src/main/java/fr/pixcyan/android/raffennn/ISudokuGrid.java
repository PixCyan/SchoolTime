package fr.pixcyan.android.raffennn;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * ISudokuGrid Interface.
 *
 * <p>This interface represents a sudoku grid generator.</p>
 * @see SudokuGrid9x9
 * @author: Camille Persson
 *
 */
public interface ISudokuGrid {

    /* **** PUBLIC METHODS **** */

    /**
     * @param x the cell's row.
     * @param y the cell's column.
     * @return the value of the cell at row x and column y.
     */
    public String getValueAt(int x, int y);

    /**
     * @param x     the cell's row. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @param y     the cell's column. Warning: no control is done, but it
     *              should belong to <tt>{0;0}</tt>.
     * @return the value of the cell at row x and column y. Empty cell is denoted by " ".
     */
    public String getSolutionAt(int x, int y);

    /**
     * @param x the cell's row.
     * @param y the cell's column.
     * @param value the value of the cell at row x and column y. For
     *        empty cell use <code>ISudokuCell.HOLE_VALUE</code>.
     */
    public void setValueAt(int x, int y, int value);

    /**
     * @return a String representing the current state of the grid
     */
    public String toString();

    /**
     * Checks all the cells' value validity.
     * @return a list of invalid cells.
     * @see SudokuError
     */
    public List<SudokuError> checkGrid();

}
