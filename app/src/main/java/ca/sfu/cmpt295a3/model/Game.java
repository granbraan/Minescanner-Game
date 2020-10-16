package ca.sfu.cmpt295a3.model;

public class Game {
    private static int minesRevealed;
    private static int scansUsed;
    static Grid grid = Grid.getInstance();

    public static int getMinesRevealed() {
        return minesRevealed;
    }

    public static void setMinesRevealed(int minesRevealed) {
        Game.minesRevealed = minesRevealed;
    }

    public static int getScansUsed() {
        return scansUsed;
    }

    public static void setScansUsed(int scansUsed) {
        Game.scansUsed = scansUsed;
    }

    public static void createGame(int col, int row, int mines) {
        for(int i = 0; i < col; i++) {
            for(int j = 0; j < row; j++) {
                grid.addCell(new Cell(row, col, false, false, false));
            }
        }
    }

}

