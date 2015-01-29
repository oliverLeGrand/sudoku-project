package sudoku;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jburnham
 * Date: 11/8/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuSolver {

    private Board game;
    private String[][] possiblePlacements;
    private int passCount = 0;
    private int totalFillins = 0;
    private StopWatch timer;

    public SudokuSolver() {}

    public SudokuSolver(Board game) {
        this.game = game;
        possiblePlacements = new String[Board.DEFAULT_SIZE][Board.DEFAULT_SIZE];
        initPossiblePlacement();
    }


    public Board getGame() {
        return game;
    }

    public void setGame(Board game) {
        this.game = game;
    }

    public void solveGame() throws UnsolvableBoardException {
        passCount = 0;
        totalFillins = 0;
        int fillinsThisRound = -1;
        timer = new StopWatch();
        timer.start();
        while (!game.isBoardCompleted() && fillinsThisRound != 0) {
            fillinsThisRound = 0;
            fillinsThisRound += cleanUpSingles();
            for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
                fillinsThisRound += placeNumbersInRow(i);
                fillinsThisRound += placeNumbersInColumn(i);
            }
            //fillinsThisRound += cleanUpSingles();
            if (fillinsThisRound == 0) {
                fillinsThisRound += cleanUpSingles();
            }
            passCount++;
            totalFillins += fillinsThisRound;

        }
        if (fillinsThisRound == 0 && !game.isBoardCompleted()) {
            throw new UnsolvableBoardException("Unsolvable Board");
        }
        timer.stop();
    }

    private void initPossiblePlacement() {
        Square sq;
        for (int i = 0; i < Board.DEFAULT_SIZE; i++)
            for (int j = 0; j < Board.DEFAULT_SIZE; j++)
                possiblePlacements[i][j] = "x";

        for (int val = 1; val <= Board.DEFAULT_SIZE; val++) {
            for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
                for (int j = 0; j < Board.DEFAULT_SIZE; j++) {
                    if (!possiblePlacements[i][j].contains(val + "")) {
                        sq = game.getSquare(i, j);
                        if (sq.getValue() == 0) {
                            if (this.canPlace(i, j, val)) {
                                //System.out.println(" - can place value: " + val + " in " + i + ", " + j);
                                if (possiblePlacements[i][j] == "x")
                                    possiblePlacements[i][j] = ",";
                                possiblePlacements[i][j] += val + ",";
                                //if (i == 3 && j == 1) System.out.println("init -> 3, 1 => " + possiblePlacements[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }

    private int placeNumbersInRow(int row) {
        if (game.isRowCompleted(row))
            return 0;

        initPossiblePlacement();

        int updated = 0;
        List<String> missingNumbers = new ArrayList<String>();
        List<String> placedNumbers = new ArrayList<String>();
        for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
            int temp = game.getSquare(row, i).getValue();
            if (temp != 0)
                placedNumbers.add(temp + "");
        }
        for (int val = 1; val <= Board.DEFAULT_SIZE; val++) {
            if (!placedNumbers.contains(val + "")) {
                missingNumbers.add(val + "");
            }
        }

        List<PlaceableNumber> placeables = new ArrayList<PlaceableNumber>();
        for (int val = 1; val <= Board.DEFAULT_SIZE; val++) {
            placeables.add(new PlaceableNumber(val));
        }
        for (int i = 0; i < missingNumbers.size(); i++) {
            for (int j = 0; j < Board.DEFAULT_SIZE; j++) {
                if(possiblePlacements[row][j].contains(missingNumbers.get(i))) {
                    placeables.get(Integer.parseInt(missingNumbers.get(i))-1).increaseCount();
                }
            }
        }

        //System.out.println("placeables in row " + row);
        for (int i = 0; i < placeables.size(); i++) {
            //System.out.println("\t" + placeables.get(i).toString());
            if (placeables.get(i).getCnt() == 1) {
                for (int j = 0; j < Board.DEFAULT_SIZE; j++) {
                    if (possiblePlacements[row][j].contains(placeables.get(i).getVal() + "")) {
                        game.getSquare(row, j).setValue(placeables.get(i).getVal());
                        //if (row == 3 && j == 1) System.out.println("3, 1 change to x from => " + possiblePlacements[row][j]);
                        possiblePlacements[row][j] = "x";
                        updated++;
                        initPossiblePlacement();
                        //return placeNumbersInRow(row);
                    }
                }

            }
        }

        if (updated > 0)
            return updated + placeNumbersInRow(row);

        return updated;
    }

    private int placeNumbersInColumn(int col) {
        if (game.isColumnCompleted(col))
            return 0;

        initPossiblePlacement();

        int updated = 0;
        List<String> missingNumbers = new ArrayList<String>();
        List<String> placedNumbers = new ArrayList<String>();
        for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
            int temp = game.getSquare(i, col).getValue();
            if (temp != 0)
                placedNumbers.add(temp + "");
        }
        for (int val = 1; val <= Board.DEFAULT_SIZE; val++) {
            if (!placedNumbers.contains(val + "")) {
                missingNumbers.add(val + "");
            }
        }

        List<PlaceableNumber> placeables = new ArrayList<PlaceableNumber>();
        for (int val = 1; val <= Board.DEFAULT_SIZE; val++) {
            placeables.add(new PlaceableNumber(val));
        }
        for (int i = 0; i < missingNumbers.size(); i++) {
            for (int j = 0; j < Board.DEFAULT_SIZE; j++) {
                if(possiblePlacements[j][col].contains(missingNumbers.get(i))) {
                    placeables.get(Integer.parseInt(missingNumbers.get(i))-1).increaseCount();
                }
            }
        }

        /*System.out.println("placeables: ");*/
        for (int i = 0; i < placeables.size(); i++) {
            //System.out.println("\t" + placeables.get(i).toString());
            if (placeables.get(i).getCnt() == 1) {
                for (int j = 0; j < Board.DEFAULT_SIZE; j++) {
                    if (possiblePlacements[j][col].contains(placeables.get(i).getVal() + "")) {
                        game.getSquare(j, col).setValue(placeables.get(i).getVal());
                        //if (i == 3 && col == 1) System.out.println("3, 1 change to x from => " + possiblePlacements[i][col]);
                        possiblePlacements[j][col] = "x";
                        updated++;
                        break;
                    }
                }

            }
        }

        if (updated > 0)
            return updated + placeNumbersInRow(col);

        return updated;
    }

    private void placeNumbersInSection(int section) {
        if (game.isSectionCompleted(section))
            return;

    }

    private boolean canPlace(int row, int col, int value) {
        boolean inRow = game.doesRowContain(row, value);
        boolean inCol = game.doesColumnContain(col, value);
        int section = game.calcSectionFromRowColumn(row, col);
        boolean inSection = game.doesSectionContain(section, value);

        return (!inRow && !inCol && !inSection);
    }

    private int cleanUpSingles() {
        boolean changed = true;
        int changesMade = 0;
        int totalChangesMade = 0;
        initPossiblePlacement();
        while (changed) {
            changed = false;
            changesMade = 0;
            for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
                for (int j = 0; j < Board.DEFAULT_SIZE; j++) {
                    if (StringUtils.countMatches(possiblePlacements[i][j], ",") == 2) {
                        int temp = Integer.parseInt(possiblePlacements[i][j].replace(",", ""));
                        possiblePlacements[i][j] = "x";
                        game.getSquare(i, j).setValue(temp);
                        changed = true;
                        changesMade++;
                        initPossiblePlacement();
                    }
                }
            }
            totalChangesMade += changesMade;
            //System.out.println("clean up pass through -> " + changesMade);
        }
        return totalChangesMade;
    }

    private boolean canPlaceSquare(int row, int col, int section, int value) {
        int cnt = 0;



        return true;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getTotalFillins() {
        return totalFillins;
    }

    public float getSolutionTime() {
        if (timer == null)
            return 0;
        float time = (float) (timer.getTime() / 1000.0);
        return time;
    }

    public void printPossiblePlacements() {

        //find the longest string
        int longest = 0;
        int tempLen = 0;
        for (int i = 0; i < Board.DEFAULT_SIZE; i++ ) {
            for (int j = 0; j < Board.DEFAULT_SIZE; j++ ) {
                tempLen = possiblePlacements[i][j].length();
                if (tempLen > longest)
                    longest = tempLen;
            }
        }
        System.out.println("longest is " + longest);

        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < Board.DEFAULT_SIZE; i++ ) {
            int j = 0;
            String value;
            for (; j < 3; j++) {
                value = possiblePlacements[i][j];
                if (value == "x")
                    value = "*" + this.game.getSquare(i, j).getValue();
                value = StringUtils.leftPad(value, longest+1);
                ret.append(value);
            }
            ret.append(" |");
            for (; j < 6; j++) {
                value = possiblePlacements[i][j];
                if (value == "x")
                    value = "*" + this.game.getSquare(i, j).getValue();
                value = StringUtils.leftPad(value, longest+1);
                ret.append(value);
            }
            ret.append(" |");
            for (; j < 9; j++) {
                value = possiblePlacements[i][j];
                if (value == "x")
                    value = "*" + this.game.getSquare(i, j).getValue();
                value = StringUtils.leftPad(value, longest+1);
                ret.append(value);
            }
            if (i == 2 || i == 5)
                ret.append("\n" + StringUtils.leftPad("-", ((longest+1)*3)+1, '-') + "+" +
                                    StringUtils.leftPad("-", ((longest+1)*3)+1, '-') + "+" +
                                    StringUtils.leftPad("-", ((longest+1)*3)+1, '-') + "\n");
            else
                ret.append("\n");

        }

        System.out.println(ret.toString());

    }

    //NOTE:
    //best way to place a number in a square is find the only spot a number can be placed on the row/col/section
    //if a number can be placed in multiple places, move along



    private class PlaceableNumber {

        private int val;
        private int cnt;

        public PlaceableNumber() {
            this.val = 0;
            this.cnt = 0;
        }

        public PlaceableNumber(int val) {
            this.val = val;
            this.cnt = 0;
        }

        public PlaceableNumber(int val, int cnt) {
            this.val = val;
            this.cnt = cnt;
        }

        private int getVal() {
            return val;
        }

        private void setVal(int val) {
            this.val = val;
        }

        private int getCnt() {
            return cnt;
        }

        private void setCnt(int cnt) {
            this.cnt = cnt;
        }

        public void increaseCount() {
            this.cnt++;
        }

        public String toString() {
            return "Value: " + val + " - Count: " + cnt;
        }
    }

    public class PlaceSquare {
        int row;
        int col;
        int number;

        public PlaceSquare(int row, int col, int number) {
            this.row = row;
            this.col = col;
            this.number = number;
        }

        public int getRow() {
            return this.row;
        }

        public int getCol() {
            return this.col;
        }

        public int getNumber() {
            return this.number;
        }
    }

}


class UnsolvableBoardException extends Exception {

    private String message = null;

    public UnsolvableBoardException() {
        super();
    }

    public UnsolvableBoardException(String message) {
        super(message);
        this.message = message;
    }

    public UnsolvableBoardException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}