package adaptiveNim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Position {
    private int[] matchsticksInRows = new int[4];
    private Rating rating;

    private int ratingIsVisibleAbove;

    public Position(int firstRow, int secondRow, int thirdRow, int fourthRow) {
        this.matchsticksInRows[0] = firstRow;
        this.matchsticksInRows[1] = secondRow;
        this.matchsticksInRows[2] = thirdRow;
        this.matchsticksInRows[3] = fourthRow;
        this.rating = Rating.UNKNOWN;
        this.ratingIsVisibleAbove = 0; //default all visible
    }

    public int[] getMatchsticksInRows() {
        int[] result = new int[4];
        result = Arrays.copyOfRange(matchsticksInRows,0,4);
        return result;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating){
        this.rating = rating;
    }

    public int getMatchsticksInRow(int row){
        return(matchsticksInRows[row]);
    }

    public int[] getPositionOrdered(){
        int[] result = new int[4];
        result = Arrays.copyOfRange(matchsticksInRows,0,4);
        result = orderRowsAsc(result);
        return result;
    }

    public boolean isEquivalent(int[] askedPosition){
        if (askedPosition.length != 4){
            return false;
        }
        boolean equals = Arrays.equals(getPositionOrdered(), orderRowsAsc(askedPosition));
        return equals;
    }

    public int getRatingIsVisibleAbove(){
        return this.ratingIsVisibleAbove;
    }

    public void setVisible(){
        if (isEquivalent(new int[]{1,1,1,0})){
                this.ratingIsVisibleAbove = 1;
                return;
        }
        if (isEquivalent(new int[] {2,2,0,0})){
            this.ratingIsVisibleAbove = 2;
            return;
        }
        int nonNullRows = getNonNullRows();
        int totalMatchsticks = getTotalMatchsticks();
        int maxMatchsticks = getMaxMatchStickInRow();

        if (nonNullRows == 2 && (totalMatchsticks/2) == maxMatchsticks){ //x-x-0-0
            this.ratingIsVisibleAbove = 3;
            return;
        }
        if (nonNullRows == 4 && ((totalMatchsticks-2)/2) == maxMatchsticks && countOneStickRows() == 2){       //x-x-1-1
            this.ratingIsVisibleAbove = 4;
            return;
        }
        if (isEquivalent(new int[]{0,1,2,3})){
            this.ratingIsVisibleAbove = 5;
            return;
        }
        if (totalMatchsticks <= 16){ //all other position evolving from original (1-3-5-7) position
            this.ratingIsVisibleAbove = 6;
            return;
        }
        this.ratingIsVisibleAbove = 7;
        return;

    }

    public List<int[]> getPossibleNextPositions(){
        int[] nextPosition;
        List<int[]> result = new ArrayList<>();
        if (matchsticksInRows[0] > 0){
            for (int i=0;i<matchsticksInRows[0];i++){
                nextPosition = new int[4];
                nextPosition[0] = i;
                nextPosition[1] = matchsticksInRows[1];
                nextPosition[2] = matchsticksInRows[2];
                nextPosition[3] = matchsticksInRows[3];
                result.add(nextPosition);
            }
        }

        if (matchsticksInRows[1] > 0){
            for (int i=0;i<matchsticksInRows[1];i++){
                nextPosition = new int[4];
                nextPosition[0] = matchsticksInRows[0];
                nextPosition[1] = i;
                nextPosition[2] = matchsticksInRows[2];
                nextPosition[3] = matchsticksInRows[3];
                result.add(nextPosition);
            }
        }

        if (matchsticksInRows[2] > 0){
            for (int i=0;i<matchsticksInRows[2];i++){
                nextPosition = new int[4];
                nextPosition[0] = matchsticksInRows[0];
                nextPosition[1] = matchsticksInRows[1];
                nextPosition[2] = i;
                nextPosition[3] = matchsticksInRows[3];
                result.add(nextPosition);
            }
        }

        if (matchsticksInRows[3] > 0){
            for (int i=0;i<matchsticksInRows[3];i++){
                nextPosition = new int[4];
                nextPosition[0] = matchsticksInRows[0];
                nextPosition[1] = matchsticksInRows[1];
                nextPosition[2] = matchsticksInRows[2];
                nextPosition[3] = i;
                result.add(nextPosition);
            }
        }
        return result;
    }

    private int getMaxMatchStickInRow(){
        int result = 0;
        for (int actual :matchsticksInRows) {
            if (actual > result){
                result = actual;
            }
        }
        return result;
    }

    private int getTotalMatchsticks(){
        int result = 0;
        for (int actual :matchsticksInRows) {
            result += actual;
        }
        return result;
    }

    private int getNonNullRows(){
        int result = 4;
        for (int actual :matchsticksInRows) {
            if (actual == 0) {
                result--;
            }
        }
        return result;
    }

    private int countOneStickRows(){
        int result = 0;
        for (int actual :matchsticksInRows) {
            if (actual == 1) {
                result++;
            }
        }
        return result;
    }


    public int[] orderRowsAsc(int[] input){
        int[] result = new int[4];
        for (int i=0;i<4;i++){
            result[i] = input[i];
        }
        Arrays.sort(result);

        return result;
    }
}
