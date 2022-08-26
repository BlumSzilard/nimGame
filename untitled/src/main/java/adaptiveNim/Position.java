package adaptiveNim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Position {
    //initialized by the    constructor:
    private final int[] matchsticksInRows = new int[4];
    private Rating rating;

    //only for caching calculation results:
    private int ratingIsVisibleAbove;
    private int complexity;
    private int[] matchsticksInRowsAsc;

    /******************************
    * Constructor, simple getters *
     ******************************/

    public Position(int firstRow, int secondRow, int thirdRow, int fourthRow) {
        this.matchsticksInRows[0] = firstRow;
        this.matchsticksInRows[1] = secondRow;
        this.matchsticksInRows[2] = thirdRow;
        this.matchsticksInRows[3] = fourthRow;
        this.rating = Rating.UNKNOWN;

    }

    public int[] getMatchsticksInRows() {
        int[] result;
        result = Arrays.copyOfRange(matchsticksInRows,0,4);
        return result;
    }

    public Rating getRating() {
        return rating;
    }

    /*********
    * Setter *
     *********/
    public void setRating(Rating rating){
        this.rating = rating;
    }

     /**********************
     * Some simple methods *
     ***********************/
     public int getMatchsticksInRow(int row){
         return(matchsticksInRows[row]);
     }

     public boolean isSamePosition(Position askedPosition){
         for (int i=0;i<4;i++){
             if (this.getMatchsticksInRow(i) != askedPosition.getMatchsticksInRow(i)){
                 return false;
             }
         }
         return true;
     }

    public boolean isEquivalentPosition(Position askedPosition){
        for (int i=0;i<4;i++){
            if (this.getMatchsticksInRowsAsc()[i] != askedPosition.getMatchsticksInRowsAsc()[i]){
                return false;
            }
        }
        return true;
    }

    public void print() {
        StringBuilder toPrint = new StringBuilder();
        int matchsticks;
        for (int i = 0; i < 4; i++) {
            matchsticks = getMatchsticksInRow(i);
            toPrint.append(toSerial(i));

            for (int j = 0; j < (4 - matchsticks/2); j++) {
                toPrint.append(' ');
            }

            for (int j = 0; j < matchsticks; j++) {
                toPrint.append('|');
            }

            toPrint.append('\n');
        }
        System.out.println(toPrint);
     }

     private String toSerial(int number){
        switch (number){
            case 0: return "1st row:";
            case 1: return "2nd row:";
            case 2: return "3rd row:";
            case 3: return "4th row:";
            default: return "error";
        }
     }


    /*************************************************************
    * Get and set matchsticks in 1st-4th rows in ascending order *
     *************************************************************/
    public int[] getMatchsticksInRowsAsc(){
        if (matchsticksInRowsAsc == null){
            setMatchsticksInRowsAsc();
        }
        int[] result;
        result = Arrays.copyOfRange(matchsticksInRowsAsc,0,4);
        return result;
    }

    private void setMatchsticksInRowsAsc(){
        this.matchsticksInRowsAsc = Arrays.copyOfRange(matchsticksInRows,0,4);
        Arrays.sort(matchsticksInRowsAsc);
    }


     /*************************
     * Get and set complexity *
      *************************/
     private int howMuchDifficult(){
         return (matchsticksInRows[0]+1)*(matchsticksInRows[1]+1)*(matchsticksInRows[2]+1)*(matchsticksInRows[3]+1);
     }

     public int getComplexity(){
         if (this.complexity == 0){              //=>not initialized yet
            this.complexity = howMuchDifficult();
         }
         return this.complexity;
     }

    /***********************************
    * Get and set ratingIsVisibleAbove *
     ***********************************/
    //ratingIsVisibleAbove = the computer's AI can use this knowledge above this level

    public int getRatingIsVisibleAbove(){
        if (this.ratingIsVisibleAbove == 0){  //=> not initialized yet
            setVisible();
        }
        return this.ratingIsVisibleAbove;
    }
    private void setVisible(){
        if (isEquivalentPosition(new Position(1,0,0,0))){
            this.ratingIsVisibleAbove = 1;
            return;
        }

        if (isEquivalentPosition(new Position(1,1,1,0))){
            this.ratingIsVisibleAbove = 2;
            return;
        }
        if (isEquivalentPosition(new Position(2,2,0,0))){
            this.ratingIsVisibleAbove = 3;
            return;
        }
        int nonNullRows = getNonNullRows();
        int totalMatchsticks = getTotalMatchsticks();
        int maxMatchsticks = getMaxMatchStickInRow();

        if (nonNullRows == 2 && (totalMatchsticks/2) == maxMatchsticks){ //x-x-0-0
            this.ratingIsVisibleAbove = 4;
            return;
        }
        if (nonNullRows == 4 && ((totalMatchsticks-2)/2) == maxMatchsticks && countOneStickRows() == 2){       //x-x-1-1
            this.ratingIsVisibleAbove = 5;
            return;
        }
        if (isEquivalentPosition(new Position(0,1,2,3))){
            this.ratingIsVisibleAbove = 6;
            return;
        }
        if (totalMatchsticks <= 16){ //all other position evolving from original (1-3-5-7) position
            this.ratingIsVisibleAbove = 7;
            return;
        }
        this.ratingIsVisibleAbove = 8;
    }

    /*********************************
    * private methods for setVisible *
     *********************************/
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

    /*************************************************************
    * Get List about possible next positions (evolved from this) *
    **************************************************************/
    public List<Position> getPossibleNextPositions(){
        Position nextPosition;
        int[] nextMatchsticksInRows = new int[4];
        List<Position> result = new ArrayList<>();
        if (matchsticksInRows[0] > 0){
            for (int i=0;i<matchsticksInRows[0];i++){
                nextMatchsticksInRows[0] = i;
                nextMatchsticksInRows[1] = matchsticksInRows[1];
                nextMatchsticksInRows[2] = matchsticksInRows[2];
                nextMatchsticksInRows[3] = matchsticksInRows[3];

                nextPosition = new Position(nextMatchsticksInRows[0],
                        nextMatchsticksInRows[1],
                        nextMatchsticksInRows[2],
                        nextMatchsticksInRows[3]);
                result.add(nextPosition);
            }
        }

        if (matchsticksInRows[1] > 0){
            for (int i=0;i<matchsticksInRows[1];i++){

                nextMatchsticksInRows[0] = matchsticksInRows[0];
                nextMatchsticksInRows[1] = i;
                nextMatchsticksInRows[2] = matchsticksInRows[2];
                nextMatchsticksInRows[3] = matchsticksInRows[3];

                nextPosition = new Position(nextMatchsticksInRows[0],
                        nextMatchsticksInRows[1],
                        nextMatchsticksInRows[2],
                        nextMatchsticksInRows[3]);
                result.add(nextPosition);
            }
        }

        if (matchsticksInRows[2] > 0){
            for (int i=0;i<matchsticksInRows[2];i++){

                nextMatchsticksInRows[0] = matchsticksInRows[0];
                nextMatchsticksInRows[1] = matchsticksInRows[1];
                nextMatchsticksInRows[2] = i;
                nextMatchsticksInRows[3] = matchsticksInRows[3];

                nextPosition = new Position(nextMatchsticksInRows[0],
                        nextMatchsticksInRows[1],
                        nextMatchsticksInRows[2],
                        nextMatchsticksInRows[3]);
                result.add(nextPosition);
            }
        }

        if (matchsticksInRows[3] > 0){
            for (int i=0;i<matchsticksInRows[3];i++){

                nextMatchsticksInRows[0] = matchsticksInRows[0];
                nextMatchsticksInRows[1] = matchsticksInRows[1];
                nextMatchsticksInRows[2] = matchsticksInRows[2];
                nextMatchsticksInRows[3] = i;

                nextPosition = new Position(nextMatchsticksInRows[0],
                        nextMatchsticksInRows[1],
                        nextMatchsticksInRows[2],
                        nextMatchsticksInRows[3]);
                result.add(nextPosition);
            }
        }
        return result;
    }
}
