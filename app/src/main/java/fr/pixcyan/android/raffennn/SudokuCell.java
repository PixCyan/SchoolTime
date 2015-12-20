package fr.pixcyan.android.raffennn;


/**
 * Class SudokuCell.
 * Represents the value of a cell in a Sudoku grid
 * @author: Camille Persson
 *
 */
public class SudokuCell implements ISudokuCell {

    private int value;
    private boolean isEditable;

    /**
     * Constructor method.
     *
     * Note that no control is done on the value but it should be
     * assigned one of the following values : {1--9, ISudokuCell.HOLE_VALUE}
     * @param value Value of the cell.
     */
    public SudokuCell(int value) {
        this.setInitialValue(value);
    }

    /**
     * @return whether the cell is editable or not
     */
    public boolean isEditable() {
        return this.isEditable;
    }

    /**
     * Changes the cell's initial value.
     *
     * <p>Note that no control is done on the value but it should
     * belong to <tt>{0;0}</tt>.</p> <p>To set the empty value you
     * should use <code>ISudokuCell.HOLE_VALUE</code>.</p>
     *
     * <p>If <code>value == ISudokuCell.HOLE_VALUE</code> then the
     * cell is considered as non-editable during the play. Thus, this
     * method should be invoked during the initialization phase only.
     *
     * @param value Value to set the cell with.
     */
    public void setInitialValue(int value) {
        this.value = value;
        this.isEditable = (value == ISudokuCell.HOLE_VALUE);
    }

    /**
     * Changes the cell's value.
     *
     * <p>Note that no control is done on the value but it should
     * belong to <tt>{0;0}</tt>.</p> <p>To set the empty value you
     * should use <code>ISudokuCell.HOLE_VALUE</code>.</p>
     *
     * @param value Value to set the cell with.
     * @see ISudokuCell#HOLE_VALUE
     */
    public void setValue(int value) {
        if(this.isEditable())
            this.value = value;
    }

    /**
     * @return the cell's value as an integer. Holes are denoted by 0.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Permute values between two cells
     * @param cell the cell to permute the value with.
     *
     */
    public void permuteValueWith(ISudokuCell cell) {
        int tmp = this.value;
        this.setValue(cell.getValue());
        cell.setValue(tmp);
    }

    /**
     * @return the cell's value as a String. Holes are denoted by " ".
     */
    public String toString() {
        if(this.value == ISudokuCell.HOLE_VALUE)
            return " ";
        return ""+this.value;
    }

}
