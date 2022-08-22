package adaptiveNim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WisdomAboutPositions {
    private List<Position> allPositions = new ArrayList<>();
    private final static int[] MAX_OF_ROWS = {7,7,7,7};

    public WisdomAboutPositions() {
        populateAllPositions();
        //setPositionLooser(new int[]{0,0,0,0}); //the looser take the last matchstick, and leaves this position on the table
        ratePositions();
    }

    public boolean isWinner(int[] thisFour){
        boolean actualListItemIsWinner;
        boolean positionsAreEquivalent;
        for (Position actualListItem: allPositions) {
            actualListItemIsWinner = (actualListItem.getRating() == Rating.VINNER);
            positionsAreEquivalent = actualListItem.isEquivalent(thisFour);
            if (actualListItemIsWinner && positionsAreEquivalent){
                return true;
            }
        }
        return false;
    }

    public boolean isLooser(int[] thisFour){
        boolean actualListItemIsLooser;
        boolean positionsAreEquivalent;
        for (Position actualListItem: allPositions) {
            actualListItemIsLooser = (actualListItem.getRating() == Rating.LOOSER);
            positionsAreEquivalent = actualListItem.isEquivalent(thisFour);
            if (actualListItemIsLooser && positionsAreEquivalent){
                return true;
            }
        }
        return false;
    }

    public int getNumberOfUnknown(){
        int result = 0;
        for (Position actual:allPositions){
            if (actual.getRating() == Rating.UNKNOWN) {
                result++;
            }
        }
        return result;
    }

    public int[] getMaxOfRows(){
        int[] result = new int[4];
        result = Arrays.copyOf(MAX_OF_ROWS,4);
        return result;
    }

    public int getMaxOfRow(int row){
        return MAX_OF_ROWS[row];
    }
    public void printInformations(){
        System.out.println("A lista hossza: " + allPositions.size());
        System.out.println("A nyertes pozíciók:");
        for (Position actual: allPositions) {
            if (actual.getRating() == Rating.VINNER){
                System.out.println(Arrays.toString(actual.getMatchsticksInRows()));
            }
        }
        System.out.println("A vesztes pozíciók:");
        for (Position actual: allPositions) {
            if (actual.getRating() == Rating.LOOSER){
                System.out.println(Arrays.toString(actual.getMatchsticksInRows()));
            }
        }
        System.out.println("Az ismeretlen pozíciók:");
        for (Position actual: allPositions) {
            if (actual.getRating() == Rating.UNKNOWN){
                System.out.println(Arrays.toString(actual.getMatchsticksInRows()));
            }
        }
    }


    private void populateAllPositions(){
        for (int i=0;i<=MAX_OF_ROWS[0];i++){
            for (int j=0;j<=MAX_OF_ROWS[1];j++){
                for (int k=0;k<=MAX_OF_ROWS[2];k++){
                    for (int l=0;l<=MAX_OF_ROWS[3];l++){
                        Position newPosition = new Position(i,j,k,l);
                        allPositions.add(newPosition);
                    }
                }
            }
        }
    }

    private void ratePositions(){
        setPositionLooser(new int[]{0,0,0,0}); //the looser take the last matchstick, and leaves this position on the table
        List<int[]> winnerCanditateAllNextPositions;
        for (int i=0;i<allPositions.size();i++){
            if (allPositions.get(i).getRating() == Rating.UNKNOWN){ //checking UNKNOWN if they are winner
                winnerCanditateAllNextPositions = allPositions.get(i).getPossibleNextPositions();
                if (allOfTheseAreLooser(winnerCanditateAllNextPositions)){ //new winner found
                    allPositions.get(i).setRating(Rating.VINNER);
                    markPossiblePreviousPositionsAsLooser(allPositions.get(i));
                    i = 0; //New information, so the search has to start all over again
                }
            }
        }

    }

    private void markPossiblePreviousPositionsAsLooser(Position winnerPosition){
        int[] winnerFour = winnerPosition.getMatchsticksInRows();
        List<int[]> loosers = new ArrayList<>();
        loosers = getPossiblePreviousPosition(winnerFour);
        for (int[] actual:loosers) {
            setPositionLooser(actual);
        }
    }

    public List<int[]> getPossiblePreviousPosition(int[] actualPositon){
        int[] previousPosition;
        List<int[]> result = new ArrayList<>();
        int thisRowMaxMatchsticks;

        thisRowMaxMatchsticks = this.getMaxOfRow(0);
        if (actualPositon[0]<thisRowMaxMatchsticks){

            for (int i=actualPositon[0]+1;i<=thisRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = i;
                previousPosition[1] = actualPositon[1];
                previousPosition[2] = actualPositon[2];
                previousPosition[3] = actualPositon[3];
                result.add(previousPosition);
            }
        }

        thisRowMaxMatchsticks = this.getMaxOfRow(1);
        if (actualPositon[1]<thisRowMaxMatchsticks){

            for (int i=actualPositon[1]+1;i<=thisRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = actualPositon[0];
                previousPosition[1] = i;
                previousPosition[2] = actualPositon[2];
                previousPosition[3] = actualPositon[3];
                result.add(previousPosition);
            }
        }

        thisRowMaxMatchsticks = this.getMaxOfRow(2);
        if (actualPositon[2]<thisRowMaxMatchsticks){

            for (int i=actualPositon[2]+1;i<=thisRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = actualPositon[0];
                previousPosition[1] = actualPositon[1];
                previousPosition[2] = i;
                previousPosition[3] = actualPositon[3];
                result.add(previousPosition);
            }
        }

        thisRowMaxMatchsticks = this.getMaxOfRow(3);
        if (actualPositon[3]<thisRowMaxMatchsticks){

            for (int i=actualPositon[3]+1;i<=thisRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = actualPositon[0];
                previousPosition[1] = actualPositon[1];
                previousPosition[2] = actualPositon[2];
                previousPosition[3] = i;
                result.add(previousPosition);
            }
        }

        return result;
    }

    private boolean allOfTheseAreLooser(List<int[]> listForCheck){
        boolean actualNotLooser;
        for (int[] actual:listForCheck){
            actualNotLooser = !isLooser(actual);
            if (actualNotLooser){
                return false;
            }
        }
        return true;
    }

    private void setPositionWinner(int[] positionToSet){
        for (Position actual:allPositions){
            if (actual.isEquivalent(positionToSet)){
                actual.setRating(Rating.VINNER);
            }
        }
    }

    private void setPositionLooser(int[] positionToSet){
        for (Position actual:allPositions){
            if (actual.isEquivalent(positionToSet)){
                actual.setRating(Rating.LOOSER);
            }
        }
    }
}
