package sudoku;

/**
 * Created with IntelliJ IDEA.
 * User: jburnham
 * Date: 11/8/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Board {
    public final static int DEFAULT_SIZE = 9;
    private Square[][] board;


    public Board() {
        board = new Square[DEFAULT_SIZE][DEFAULT_SIZE];
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                board[i][j] = new Square();
            }
        }
    }

    public Square[][] getBoard() {
        return this.board;
    }

    public Square getSquare(int row, int col) {
        if (row < 0 || row >= DEFAULT_SIZE ||col < 0 || col >= DEFAULT_SIZE)
            return null;

        return board[row][col];

    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < DEFAULT_SIZE; i++ ) {
            int j = 0;
            String value;
            for (; j < 3; j++) {
                value = " ";
                if (board[i][j].getValue() != 0)
                    value = board[i][j].getValue() + "";
                ret.append((board[i][j].getCanUpdate() ? " " : "*") + value);
            }
            ret.append(" |");
            for (; j < 6; j++) {
                value = " ";
                if (board[i][j].getValue() != 0)
                    value = board[i][j].getValue() + "";
                ret.append((board[i][j].getCanUpdate() ? " " : "*") + value);
            }
            ret.append(" |");
            for (; j < 9; j++) {
                value = " ";
                if (board[i][j].getValue() != 0)
                    value = board[i][j].getValue() + "";
                ret.append((board[i][j].getCanUpdate() ? " " : "*") + value);
            }
            if (i == 2 || i == 5)
                ret.append("\n-------+-------+-------\n");
            else
                ret.append("\n");

        }

        return ret.toString();
    }

    public void addInitial(int row, int col, int val) {
        if (row >= 0 && row < DEFAULT_SIZE && col >= 0 && col < DEFAULT_SIZE && val >= 0 && val <= DEFAULT_SIZE) {
            board[row][col].setGameSquare(val);
        }
    }

    public void addGuess(int row, int col, int val) {
        if (row >= 0 && row < DEFAULT_SIZE && col >= 0 && col < DEFAULT_SIZE && val >= 0 && val < DEFAULT_SIZE) {
            if (board[row][col].getCanUpdate())
                board[row][col].setPlaySquare(val);
        }
    }

    //resets all user guesses
    public void reset() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                if (board[i][j].getCanUpdate() )
                    board[i][j].setPlaySquare(0);
            }
        }
    }

    //clears the board
    public void clear() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            for (int j = 0; j < DEFAULT_SIZE; j++) {
                board[i][j].setGameSquare(0);
            }
        }
    }

    //check the row for completion
    public boolean isRowCompleted(int row) {
        if (row < 0 || row > DEFAULT_SIZE-1) {
            //System.out.println("Row complete conflict.  Row: " + row + " is too big");
            return false;
        }

        int temp = 0;
        for (int j = 0; j < DEFAULT_SIZE-1; j++) {
            temp = board[row][j].getValue();
            for (int k = j+1; k < DEFAULT_SIZE; k++) {
                if (temp == board[row][k].getValue()) {
                    //System.out.println("Row complete conflict (" + row + "): " + row + ", " + j + " - "  + row + ", " + k + " : " + temp);
                    return false;
                }
            }
        }

        return true;
    }

    //check the row for safe placement
    public boolean isRowSafe(int row) {
        if (row < 0 || row > DEFAULT_SIZE-1) {
            //System.out.println("Row safe conflict.  Row: " + row + " is too big");
            return false;
        }

        int temp = 0;
        for (int j = 0; j < DEFAULT_SIZE-1; j++) {
            temp = board[row][j].getValue();
            for (int k = j+1; k < DEFAULT_SIZE; k++) {
                if (temp == board[row][k].getValue() && temp != 0) {
                    //System.out.println("Row safe conflict (" + row + "): " + row + ", " + j + " - "  + row + ", " + k + " : " + temp);
                    return false;
                }
            }
        }

        return true;
    }

    //check the column for completion
    public boolean isColumnCompleted(int col) {
        if (col < 0 || col > DEFAULT_SIZE-1) {
            //System.out.println("Column complete conflict.  Column: " + col + " is too big");
            return false;
        }

        int temp = 0;
        for (int j = 0; j < DEFAULT_SIZE-1; j++) {
            temp = board[j][col].getValue();
            for (int k = j+1; k < DEFAULT_SIZE; k++) {
                if (temp == board[k][col].getValue()) {
                    //System.out.println("Column complete conflict (" + col + "): " + j + ", " + col + " - "  + k + ", " + col + " : " + temp);
                    return false;
                }
            }
        }

        return true;
    }

    //check the column for safe placement
    public boolean isColumnSafe(int col) {
        if (col < 0 || col > DEFAULT_SIZE-1) {
            //System.out.println("Column safe conflict.  Column: " + col + " is too big");
            return false;
        }

        int temp = 0;
        for (int j = 0; j < DEFAULT_SIZE-1; j++) {
            temp = board[j][col].getValue();
            for (int k = j+1; k < DEFAULT_SIZE; k++) {
                if (temp == board[k][col].getValue() && temp != 0) {
                    //System.out.println("Row safe conflict (" + col + "): " + j + ", " + col + " - "  + k + ", " + col + " : " + temp);
                    return false;
                }
            }
        }

        return true;
    }

    //check the section for completion
    public boolean isSectionCompleted(int section) {
        if (section < 0 || section > DEFAULT_SIZE-1) {
            //System.out.println("Section complete conflict.  Section: " + section + " is too big");
            return false;
        }

        int startRow = calcRowFromSection(section);
        int startCol = calcColumnFromSection(section);

        int temp = 0;
        for (int i = startRow; i < startRow+3; i++) {
            for (int j = startCol; j < startCol+3; j++) {
                temp = board[i][j].getValue();
                //System.out.print(" - " + i + ", " + j + " -> " + temp);
                for (int k = i; k < startRow+3; k++) {
                    for (int l = j; l < startCol+3; l++) {
                        if (i != k && l != k) {
                            if (temp == board[k][l].getValue()) {
                                System.out.println("Section complete conflict (" + section + "): " + i + ", " + j+ " - "  + k + ", " + l + " : " + temp);
                                System.out.println("started at " + startRow + " , " + startCol);
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    //check the section for safe placement
    public boolean isSectionSafe(int section) {
        if (section < 0 || section > DEFAULT_SIZE-1) {
            //System.out.println("Section safe conflict.  Section: " + section + " is too big");
            return false;
        }

        int startRow = calcRowFromSection(section);
        int startCol = calcColumnFromSection(section);

        int temp = 0;
        for (int i = startRow; i < startRow+3-1; i++) {
            for (int j = startCol; j < startCol+3-1; j++) {
                temp = board[i][j].getValue();
                for (int k = i; k < startRow+3; k++) {
                    for (int l = k; l < startCol+3; l++) {
                        if (i != k && l != k) {
                            if (temp == board[k][l].getValue() && temp != 0) {
                                //System.out.println("Section safe conflict (" + section + "): " + i + ", " + j+ " - "  + k + ", " + l + " : " + temp);
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public int calcColumnFromSection(int section) {
        int col = 0;
        if (section == 0 || section == 3 || section == 6)
            col = 0;
        if (section == 1 || section == 4 || section == 7)
            col = 3;
        if (section == 2 || section == 5 || section == 8)
            col = 6;

        return col;
    }

    public int calcRowFromSection(int section) {
        int row = 0;
        if (section >= 0 && section <= 2)
            row = 0;
        else if (section >= 3 && section <= 5)
            row = 3;
        else if (section >= 6 && section <= 8)
            row = 6;

        return row;
    }

    public int calcSectionFromRowColumn(int row, int col) {
        int section = 0;

        if ((row >= 0 && row <= 2) && (col >= 0 && col <= 2))
            section = 0;
        else if ((row >= 0 && row <= 2) && (col >= 3 && col <= 5))
            section = 1;
        else if ((row >= 0 && row <= 2) && (col >= 6 && col <= 8))
            section = 2;

        else if ((row >= 3 && row <= 5) && (col >= 0 && col <= 2))
            section = 3;
        else if ((row >= 3 && row <= 5) && (col >= 3 && col <= 5))
            section = 4;
        else if ((row >= 3 && row <= 5) && (col >= 6 && col <= 8))
            section = 5;

        else if ((row >= 6 && row <= 8) && (col >= 0 && col <= 2))
            section = 6;
        else if ((row >= 6 && row <= 8) && (col >= 3 && col <= 5))
            section = 7;
        else if ((row >= 6 && row <= 8) && (col >= 6 && col <= 8))
            section = 8;

        return section;
    }

    //check the board for completion
    public boolean isBoardCompleted() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            if (!isRowCompleted(i))
                return  false;
            if (!isColumnCompleted(i))
                return false;
            if (!isSectionCompleted(i))
                return false;
        }

        return true;
    }

    //check the board for safe placement
    public boolean isBoardSafe() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            if (!isRowSafe(i)) {
                return  false;
            }
            if (!isColumnSafe(i)) {
                return false;
            }
            if (!isSectionSafe(i)) {
                //System.out.println("Board not safe. Section " + i);
                return false;
            }
        }

        return true;
    }

    public boolean doesRowContain(int row, int value) {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            if (board[row][i].getValue() == value)
                return true;
        }
        return false;
    }

    public boolean doesColumnContain(int col, int value) {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            if (board[i][col].getValue() == value)
                return true;
        }
        return false;
    }

    public boolean doesSectionContain(int section, int value) {
        int startRow = calcRowFromSection(section);
        int startCol = calcColumnFromSection(section);

        int temp = 0;
        for (int i = startRow; i < startRow+3; i++) {
            for (int j = startCol; j < startCol+3; j++) {
                if (board[i][j].getValue() == value)
                    return true;
            }
        }

        return false;
    }

}


