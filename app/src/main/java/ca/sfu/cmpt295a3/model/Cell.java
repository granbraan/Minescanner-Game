package ca.sfu.cmpt295a3.model;

public class Cell {
    private int row;
    private int col;
    private boolean isMine;
    private boolean isReveal;
    private boolean isScanned;

    public Cell(int row, int col, boolean isMine, boolean isReveal, boolean isScanned) {
        this.row = row;
        this.col = col;
        this.isMine = isMine;
        this.isReveal = isReveal;
        this.isScanned = isScanned;
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
