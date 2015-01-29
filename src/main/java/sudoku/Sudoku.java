package sudoku;

/**
 * Created with IntelliJ IDEA.
 * User: jburnham
 * Date: 11/8/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class Sudoku {
    public Sudoku() {

    }


    public static void main(String args[]) {
        doBoard("Easy");
        doBoard("Medium");
        doBoard("Hard");
        doBoard("Very Hard");
    }

    private static void doBoard(String difficulty) {
        Board game = new Board();
        if (difficulty == "Easy") {
            setEasyBoard(game);
        }
        else if (difficulty == "Medium") {
            setMediumBoard(game);
        }
        else if (difficulty == "Hard") {
            setHardBoard(game);
        }
        else if (difficulty == "Very Hard") {
            setVeryHardBoard(game);
        }
        else {
            System.out.println("Difficulty '" + difficulty + "' is not supported");
            return;
        }

        System.out.println("============================= ");
        SudokuSolver ss = new SudokuSolver(game);
        printBoardStats(ss, game, difficulty);
        try {
            ss.solveGame();
            printBoardStats(ss, game, difficulty);
        } catch (UnsolvableBoardException e) {
            e.printStackTrace();
            ss.printPossiblePlacements();
            printBoardStats(ss, game, difficulty);
        }

    }

    private static void printBoardStats(SudokuSolver ss, Board game, String difficulty) {
        System.out.println("----------------------------- ");
        System.out.println(difficulty + " Board: ");
        System.out.println("Passes  : " + ss.getPassCount());
        System.out.println("Fillins : " + ss.getTotalFillins());
        System.out.println("Time    : " + ss.getSolutionTime() + " secs");
        System.out.println("----------------------------- ");
        System.out.println(game);
        System.out.println("============================= ");

    }

    private static void setEasyBoard(Board game) {
        game.addInitial(0,1,6);
        game.addInitial(0,3,3);
        game.addInitial(0,6,8);
        game.addInitial(0,8,4);

        game.addInitial(1,0,5);
        game.addInitial(1,1,3);
        game.addInitial(1,2,7);
        game.addInitial(1,4,9);

        game.addInitial(2,1,4);
        game.addInitial(2,5,6);
        game.addInitial(2,6,3);
        game.addInitial(2,8,7);

        game.addInitial(3,1,9);
        game.addInitial(3,4,5);
        game.addInitial(3,5,1);
        game.addInitial(3,6,2);
        game.addInitial(3,7,3);
        game.addInitial(3,8,8);


        game.addInitial(5,0,7);
        game.addInitial(5,1,1);
        game.addInitial(5,2,3);
        game.addInitial(5,3,6);
        game.addInitial(5,4,2);
        game.addInitial(5,7,4);

        game.addInitial(6,0,3);
        game.addInitial(6,2,6);
        game.addInitial(6,3,4);
        game.addInitial(6,7,1);

        game.addInitial(7,4,6);
        game.addInitial(7,6,5);
        game.addInitial(7,7,2);
        game.addInitial(7,8,3);

        game.addInitial(8,0,1);
        game.addInitial(8,2,2);
        game.addInitial(8,5,9);
        game.addInitial(8,7,8);
    }

    private static void setMediumBoard(Board game) {
        game.addInitial(0,0,4);
        game.addInitial(0,7,2);

        game.addInitial(1,0,8);
        game.addInitial(1,3,7);
        game.addInitial(1,5,9);

        game.addInitial(2,1,1);
        game.addInitial(2,2,6);
        game.addInitial(2,3,3);

        game.addInitial(3,0,5);
        game.addInitial(3,2,9);
        game.addInitial(3,7,1);

        game.addInitial(4,0,3);
        game.addInitial(4,1,7);
        game.addInitial(4,2,4);
        game.addInitial(4,3,2);
        game.addInitial(4,5,1);
        game.addInitial(4,6,5);
        game.addInitial(4,7,8);
        game.addInitial(4,8,6);

        game.addInitial(5,1,8);
        game.addInitial(5,6,7);
        game.addInitial(5,8,9);

        game.addInitial(6,5,7);
        game.addInitial(6,6,6);
        game.addInitial(6,7,3);

        game.addInitial(7,3,8);
        game.addInitial(7,5,5);
        game.addInitial(7,8,4);

        game.addInitial(8,1,9);
        game.addInitial(8,8,7);

    }

    private static void setHardBoard(Board game) {
        game.addInitial(0,2,4);
        game.addInitial(0,4,5);
        game.addInitial(0,7,6);

        game.addInitial(1,1,6);
        game.addInitial(1,3,1);
        game.addInitial(1,6,8);
        game.addInitial(1,8,9);

        game.addInitial(2,0,3);
        game.addInitial(2,5,7);

        game.addInitial(3,1,8);
        game.addInitial(3,6,5);

        game.addInitial(4,3,4);
        game.addInitial(4,5,3);

        game.addInitial(5,2,6);
        game.addInitial(5,7,7);

        game.addInitial(6,3,2);
        game.addInitial(6,8,6);

        game.addInitial(7,0,1);
        game.addInitial(7,2,5);
        game.addInitial(7,5,4);
        game.addInitial(7,7,3);

        game.addInitial(8,1,2);
        game.addInitial(8,4,7);
        game.addInitial(8,6,1);
    }

    private static void setVeryHardBoard(Board game) {
        game.addInitial(0,1,1);
        game.addInitial(0,2,4);
        game.addInitial(0,5,3);
        game.addInitial(0,6,6);

        game.addInitial(1,0,5);
        game.addInitial(1,3,6);

        game.addInitial(2,2,7);
        game.addInitial(2,3,1);
        game.addInitial(2,4,9);
        game.addInitial(2,6,3);

        game.addInitial(3,0,8);
        game.addInitial(3,3,4);
        game.addInitial(3,6,2);

        game.addInitial(4,1,6);
        game.addInitial(4,3,2);
        game.addInitial(4,5,9);
        game.addInitial(4,7,3);

        game.addInitial(5,2,1);
        game.addInitial(5,5,6);
        game.addInitial(5,8,7);

        game.addInitial(6,2,6);
        game.addInitial(6,4,4);
        game.addInitial(6,5,5);
        game.addInitial(6,6,7);

        game.addInitial(7,5,8);
        game.addInitial(7,8,6);

        game.addInitial(8,2,9);
        game.addInitial(8,3,3);
        game.addInitial(8,6,1);
        game.addInitial(8,7,8);


    }
}
