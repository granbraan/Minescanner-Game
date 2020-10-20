package ca.sfu.cmpt295a3.model;

import java.util.ArrayList;

// Game grid which is an arraylist of Cells
public class Grid {
    static ArrayList<Cell> grid = new ArrayList<>();

    private static Grid instance;

    private Grid() {

    }

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    public Cell getCell(int index) {
        return grid.get(index);
    }

    public void addCell(Cell cell) {
        grid.add(cell);
    }

    public int size()
    {
        return grid.size();
    }

    public void restartGrid() {
        for(int i = 0; i < grid.size(); i++) {
            grid.get(i).setReveal(false);
            grid.get(i).setScanned(false);
            grid.get(i).setMine(false);
        }
    }
}
