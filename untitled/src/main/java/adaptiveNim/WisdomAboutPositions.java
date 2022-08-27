package adaptiveNim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WisdomAboutPositions {
    private List<Position> allPossiblePositions = new ArrayList<>();
    private final static int[] MAX_OF_ROWS = {9,9,9,9};

    /**************************************************
    * Constructor and private methods for constructor *
     **************************************************/
    public WisdomAboutPositions() {
        populateAllPositions();
        ratePositions();
    }

    private void populateAllPositions(){
        for (int i=0;i<=MAX_OF_ROWS[0];i++){
            for (int j=0;j<=MAX_OF_ROWS[1];j++){
                for (int k=0;k<=MAX_OF_ROWS[2];k++){
                    for (int l=0;l<=MAX_OF_ROWS[3];l++){
                        Position newPosition = new Position(i,j,k,l);
                        allPossiblePositions.add(newPosition);
                    }
                }
            }
        }
    }

    private void ratePositions(){
        setEquivalentPositionsLooser(new Position(0,0,0,0)); //the looser take the last matchstick, and leaves this position on the table
        List<Position> winnerCanditateAllNextPositions;
        for (int i = 0; i< allPossiblePositions.size(); i++){
            if (allPossiblePositions.get(i).getRating() == Rating.UNKNOWN){ //checking UNKNOWN if they are winner
                winnerCanditateAllNextPositions = allPossiblePositions.get(i).getPossibleNextPositions();
                if (allOfTheseAreLooser(winnerCanditateAllNextPositions)){ //new winner found
                    setEquivalentPositionsWinner(allPossiblePositions.get(i));
                    allPossiblePositions.get(i).setRating(Rating.VINNER);
                    markPossiblePreviousPositionsAsLooser(allPossiblePositions.get(i));
                    i = 0; //New information, so the search has to start all over again
                }
            }
        }

    }

    private boolean allOfTheseAreLooser(List<Position> listForCheck){
        for (Position actual:listForCheck){
            if (isNotLooser(actual)){
                return false;
            }
        }
        return true;
    }

    private boolean isNotLooser(Position askedPosition){
        boolean actualItemIsNotLooser;
        boolean positionsAreEquivalent;
        for (Position actualListItem: allPossiblePositions) {
            actualItemIsNotLooser = (actualListItem.getRating() != Rating.LOOSER);
            positionsAreEquivalent = actualListItem.isEquivalentPosition(askedPosition);
            if (actualItemIsNotLooser && positionsAreEquivalent){
                return true;
            }
        }
        return false;
    }

    private void markPossiblePreviousPositionsAsLooser(Position winnerPosition){
        int[] winnerFour = winnerPosition.getMatchsticksInRows();
        List<int[]> loosers = new ArrayList<>();
        loosers = getPossiblePreviousPosition(winnerFour);
        for (int[] actual:loosers) {
            setEquivalentPositionsLooser(new Position(actual[0], actual[1], actual[2], actual[3]));
        }
    }

    private List<int[]> getPossiblePreviousPosition(int[] actualPositon){
        int[] previousPosition;
        List<int[]> result = new ArrayList<>();
        int actualRowMaxMatchsticks;

        actualRowMaxMatchsticks = MAX_OF_ROWS[0];
        if (actualPositon[0]<actualRowMaxMatchsticks){

            for (int i=actualPositon[0]+1;i<=actualRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = i;
                previousPosition[1] = actualPositon[1];
                previousPosition[2] = actualPositon[2];
                previousPosition[3] = actualPositon[3];
                result.add(previousPosition);
            }
        }

        actualRowMaxMatchsticks = MAX_OF_ROWS[1];
        if (actualPositon[1]<actualRowMaxMatchsticks){

            for (int i=actualPositon[1]+1;i<=actualRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = actualPositon[0];
                previousPosition[1] = i;
                previousPosition[2] = actualPositon[2];
                previousPosition[3] = actualPositon[3];
                result.add(previousPosition);
            }
        }

        actualRowMaxMatchsticks = MAX_OF_ROWS[2];
        if (actualPositon[2]<actualRowMaxMatchsticks){

            for (int i=actualPositon[2]+1;i<=actualRowMaxMatchsticks;i++){
                previousPosition = new int[4];
                previousPosition[0] = actualPositon[0];
                previousPosition[1] = actualPositon[1];
                previousPosition[2] = i;
                previousPosition[3] = actualPositon[3];
                result.add(previousPosition);
            }
        }

        actualRowMaxMatchsticks = MAX_OF_ROWS[3];
        if (actualPositon[3]<actualRowMaxMatchsticks){

            for (int i=actualPositon[3]+1;i<=actualRowMaxMatchsticks;i++){
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

    private void setEquivalentPositionsWinner(Position positionToSet){
        for (Position actual: allPossiblePositions){
            if (actual.isEquivalentPosition(positionToSet)){
                actual.setRating(Rating.VINNER);
            }
        }
    }

    private void setEquivalentPositionsLooser(Position positionToSet){
        for (Position actual: allPossiblePositions){
            if (actual.isEquivalentPosition(positionToSet)){
                actual.setRating(Rating.LOOSER);
            }
        }
    }
    /***************************
    * methods for using wisdom *
     * *************************/

    public boolean isVisibleWinner(int[] thisFour, int level){
        boolean actualListItemIsWinner;
        boolean actualListItemIsVisible;
        boolean positionsAreSame;
        for (Position actualListItem: allPossiblePositions) {
            actualListItemIsWinner = (actualListItem.getRating() == Rating.VINNER);
            actualListItemIsVisible = (actualListItem.getRatingIsVisibleAbove() <= level);
            positionsAreSame = Arrays.equals(thisFour, actualListItem.getMatchsticksInRows());
            if (actualListItemIsWinner && actualListItemIsVisible && positionsAreSame){
                return true;
            }
        }
        return false;
    }

    public Position suggestNextStep(int gameLevel, Position actualPosition){

        List<Position> possibleSteps = actualPosition.getPossibleNextPositions();
        for (Position isItMaybe: possibleSteps){
            if (isVisibleWinner(isItMaybe.getMatchsticksInRows(),gameLevel)){
                return isItMaybe;
            }
        }
        //Plan: if winner mot found, choose the most difficult:
        //temporary: choose the first
        return possibleSteps.get(0);
    }










    /*******************
    * for testing only *
     *******************/
    public int getNumberOfUnknown(){
        int result = 0;
        for (Position actual: allPossiblePositions){
            if (actual.getRating() == Rating.UNKNOWN) {
                result++;
            }
        }
        return result;
    }

    public void printInformations(){
        System.out.println("A lista hossza: " + allPossiblePositions.size());
        System.out.println("A nyertes pozíciók:");
        for (Position actual: allPossiblePositions) {
            if (actual.getRating() == Rating.VINNER){
                System.out.println(Arrays.toString(actual.getMatchsticksInRows()));
            }
        }
        System.out.println("A vesztes pozíciók:");
        for (Position actual: allPossiblePositions) {
            if (actual.getRating() == Rating.LOOSER){
                System.out.println(Arrays.toString(actual.getMatchsticksInRows()));
            }
        }
        System.out.println("Az ismeretlen pozíciók:");
        for (Position actual: allPossiblePositions) {
            if (actual.getRating() == Rating.UNKNOWN){
                System.out.println(Arrays.toString(actual.getMatchsticksInRows()));
            }
        }
    }

}
