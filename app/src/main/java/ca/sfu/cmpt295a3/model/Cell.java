package ca.sfu.cmpt295a3.model;

public class Cell {
    private int row;
    private int col;
    private int isMine;
    private int isReveal;

    public Cell(int row, int col, int isMine, int isReveal) {
        this.row = row;
        this.col = col;
        this.isMine = isMine;
        this.isReveal = isReveal;
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

    public int getIsMine() {
        return isMine;
    }

    public void setIsMine(int isMine) {
        this.isMine = isMine;
    }

    public int getIsReveal() {
        return isReveal;
    }

    public void setIsReveal(int isReveal) {
        this.isReveal = isReveal;
    }
}
