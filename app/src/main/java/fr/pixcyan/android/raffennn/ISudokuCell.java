package fr.pixcyan.android.raffennn;

/**
 * Interface ISudokuCell.
 * Represents an abstraction of a cell in a Sudoku grid
 * @author: Camille Persson
 *
 */
public interface ISudokuCell {

    /**
     * The integer value of empty cells.
     */
    public static final int HOLE_VALUE = 0;

    /**
     * @return whether the cell is editable or not
     */
    public boolean isEditable();

    /**
     * Changes the cell's initial value.
     *
     * @param value Value to set the cell with.
     */
    public void setInitialValue(int value);

    /**
     * Changes the cell's value.
     *
     * @param value Value to set the cell with.
     */
    public void setValue(int value);

    /**
     * @return the cell's value as an integer.
     */
    public int getValue();

    /**
     * Permute values between two cells
     * @param cell the cell to permute the value with.
     *
     */
    public void permuteValueWith(ISudokuCell cell);

    /**
     * @return the cell's value as a String.
     */
    public String toString();

}
