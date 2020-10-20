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
                grid.addCell(new Cell(j, i, false, false, false,0));
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
        int scannedMines = 0;
        for(int i = 0; i < grid.size(); i++) {
                if(grid.getCell(i).getCol() == cell.getCol() || (grid.getCell(i).getRow() == cell.getRow())) {
                    if(grid.getCell(i).isMine() && !grid.getCell(i).isReveal()) {
                        scannedMines++;
                    }
                }
            }
        cell.setScanCounter(scannedMines);
    }
    //decrease scan count when mine is found
    public static void  updateScan(Cell cell) {
        for(int i = 0; i < grid.size(); i++) {
            if(grid.getCell(i).isScanned()) {
                scan(cell);
            }
        }
    }




}

