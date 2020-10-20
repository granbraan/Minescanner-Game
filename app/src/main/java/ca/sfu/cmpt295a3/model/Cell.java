package ca.sfu.cmpt295a3.model;

/**
 * Contains the information of a specific cell,
 * Where the cell is, if it's a bloon, revealed yet, scanned yet, and the number of scans (In the row/column)
 */
public class Cell {
    private int row;
    private int col;
    private boolean isMine;
    private boolean isReveal;
    private boolean isScanned;
    private int scanCounter;

    public Cell(int row, int col, boolean isMine, boolean isReveal, boolean isScanned, int scanCounter) {
        this.row = row;
        this.col = col;
        this.isMine = isMine;
        this.isReveal = isReveal;
        this.isScanned = isScanned;
        this.scanCounter = scanCounter;
    }
    public int getScanCounter() {
        return scanCounter;
    }

    public void setScanCounter(int scanCounter) {
        this.scanCounter = scanCounter;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isReveal() {
        return isReveal;
    }

    public void setReveal(boolean reveal) {
        isReveal = reveal;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }
}
