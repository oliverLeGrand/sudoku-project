package sudoku;

/**
 * Created with IntelliJ IDEA.
 * User: jburnham
 * Date: 11/8/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Square {
    private int value;
    private boolean canUpdate;

    public Square() {
        value = 0;
        canUpdate = true;
    }

    public Square(int value, boolean canUpdate) {
        this.value = value;
        this.canUpdate = canUpdate;
    }

    public int getValue() {
        return value;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    public boolean getCanUpdate() {
        return canUpdate;
    }

    protected void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public void setPlaySquare(int value) {
        this.value = value;
        this.canUpdate = true;
    }

    public void setGameSquare(int value) {
        this.value = value;
        this.canUpdate = false;
    }

    @Override
    public String toString() {
        return this.value + "";
    }
}
