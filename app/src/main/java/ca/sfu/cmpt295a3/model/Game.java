package ca.sfu.cmpt295a3.model;

public class Game {
    private static int minesRevealed;
    private static int scansUsed;

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
}

