package adaptiveNim;

import java.util.Arrays;

public class Position {
    private int[] matchsticksInRows = new int[4];
    private Rating rating;

    public Position(int firstRow, int secondRow, int thirdRow, int fourthRow) {
        this.matchsticksInRows[0] = firstRow;
        this.matchsticksInRows[1] = secondRow;
        this.matchsticksInRows[2] = thirdRow;
        this.matchsticksInRows[3] = fourthRow;
        this.rating = Rating.UNKNOWN;
    }

    public int[] getMatchsticksInRows() {
        int[] result = new int[4];
        result = Arrays.copyOfRange(matchsticksInRows,0,4);
        return result;
    }

    public Rating getRating() {
        return rating;
    }

    public int getMatchsticksInRow(int row){
        return(matchsticksInRows[row+1]);
    }
}
