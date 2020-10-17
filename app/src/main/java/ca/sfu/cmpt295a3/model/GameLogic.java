package ca.sfu.cmpt295a3.model;

import java.util.Random;

public class GameLogic {
    private static int minesRevealed;
    private static int scansUsed;
    static Grid grid = Grid.getInstance();

    public static int getMinesRevealed() {
        return minesRevealed;
    }

    public static void setMinesRevealed(int minesRevealed) {
        GameLogic.minesRevealed = minesRevealed;
    }

    public static int getScansUsed() {
        return scansUsed;
    }

    public static void setScansUsed(int scansUsed) {
        GameLogic.scansUsed = scansUsed;
    }

    public static void createGame(int col, int row, int mines) {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                grid.addCell(new Cell(row, col, false, false, false));
            }
        }

        //Place mines in field
        while (mines > 0) {
            Random random = new Random();
            int randomCell = random.nextInt(col * row); //get random cell to place mine

            //add mine if cell doesnt contain mine
            if (!(grid.getCell(randomCell)).isMine()) {
                grid.getCell(randomCell).setMine(true);
                mines--;
            }
        }
    }

    public static void scan(Cell cell) {
        if( (cell.isMine() && cell.isReveal()) || (!cell.isMine() && !cell.isReveal()) ) {
            for(int i = 0; i < grid.size(); i++) {
                if(grid.getCell(i).getCol() == cell.getCol() || (grid.getCell(i).getRow() == cell.getRow())) {
                    minesRevealed = scansUsed;
                    scansUsed = ++minesRevealed;
                }
            }
        }
    }

    public static void updateScan(Cell cell) {
        if( (cell.isMine() && cell.isReveal()) || (!cell.isMine() && !cell.isReveal()) ) {
            for(int i = 0; i < grid.size(); i++) {
                if((grid.getCell(i).getCol() == cell.getCol() || (grid.getCell(i).getRow() == cell.getRow())) && !cell.isReveal()) {
                    minesRevealed = scansUsed;
                    scansUsed = --minesRevealed;
                }
            }
        }
    }


}

