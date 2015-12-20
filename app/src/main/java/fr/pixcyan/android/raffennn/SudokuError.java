package fr.pixcyan.android.raffennn;

/**
 * Class SudokuError.
 *
 * <p> Denotes a cell containing an error in the Sudoku grid.</p>
 *
 * <p>This class implements the <code>ISudokuCell</code> interface in
 * order to embedd the original cell containing the error using the
 * <code>Decorator</code> design pattern. Hence, most of
 * <code>ISudokuCell</code> methods are not really implemented in this
 * class, but rather delegated to the embedded cell's class.</p>
 *
 * @see ISudokuCell
 * @see SudokuCell
 * @author: Camille Persson
 *
 */
public class SudokuError implements ISudokuCell {

    private int errorRow;
    private int errorCol;
    private ISudokuCell cell;

    /**
     * Constructor.
     * @param row  the row number of the cell
     * @param col  the column number of the cell
     * @param cell the original cell containing the error
     */
    public SudokuError(int row, int col, ISudokuCell cell) {
        this.errorRow = row;
        this.errorCol = col;
        this.cell = cell;
    }

    /**
     * @return the row number of the cell
     */
    public int getErrorRow() {
        return this.errorRow;
    }

    /**
     * @return the column number of the cell
     */
    public int getErrorCol() {
        return this.errorCol;
    }

    /**
     * @return the original cell containing the error
     */
    public ISudokuCell getErrorCell() {
        return this.cell;
    }
    /**
     * This method overrides the original cell's
     * <code>toString()</code> method to replace the value displayed
     * by <code>"E"</code>. It is mainly for console display
     * mainly. To get the actual value you should use the method
     * <code>getValue()</code>.
     *
     * @return the <code>"E"</code> character.
     * @see #getValue
     */
    @Override
    public String toString() {
        return "E";
    }

    /* **** ISudokuCell implementation **** */

    /**
     * @see ISudokuCell#isEditable
     */
    public boolean isEditable() {
        return this.cell.isEditable();
    }

    /**
     * @see ISudokuCell#setInitialValue
     */
    public void setInitialValue(int value) {
        this.cell.setInitialValue(value);
    }

    /**
     * @see ISudokuCell#setValue
     */
    public void setValue(int value) {
        this.cell.setValue(value);
    }

    /**
     * @see ISudokuCell#getValue
     */
    public int getValue() {
        return this.cell.getValue();
    }

    /**
     * @see ISudokuCell#permuteValueWith
     */
    public void permuteValueWith(ISudokuCell cell) {
        this.cell.permuteValueWith(cell);
    }

}
