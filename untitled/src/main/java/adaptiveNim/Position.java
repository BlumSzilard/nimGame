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

    public void setRating(Rating rating){
        this.rating = rating;
    }

    public int getMatchsticksInRow(int row){
        return(matchsticksInRows[row+1]);
    }

    public int[] getPositionOrdered(){
        int[] result = new int[4];
        result = Arrays.copyOfRange(matchsticksInRows,0,4);
        result = orderRowsDesc(result);
        return result;
    }

    public boolean isEquivalent(int[] askedPosition){
        if (askedPosition.length != 4){
            return false;
        }
        boolean equals = Arrays.equals(getPositionOrdered(), orderRowsDesc(askedPosition));
        return equals;
    }

    private int[] orderRowsDesc(int[] input){
        if (input.length != 4){
            System.out.println("Error");
        }
        boolean[] pickedUp = new boolean[4];
        int[] result = new int[4];

        for (int i=0;i<4;i++){
            pickedUp[i] = false;
            result[i] = 10; //dummy value
        }
        int indexOfMax;
        for (int i=0;i<4;i++){
            indexOfMax = 0;
            for (int j=0;j<4;j++){
                if (input[j]>input[indexOfMax] && !pickedUp[j]){
                    indexOfMax = j;
                }
            }
            result[i] = input[indexOfMax];
            pickedUp[indexOfMax] = true;
        }
        return result;
    }
}
