package adaptiveNim;

import java.util.*;

public class WisdomAboutPositions {
    private List<Position> allPossiblePositions = new ArrayList<>();
    private static final int[] MAX_OF_ROWS = {9, 9, 9, 9};
    private Random random = new Random();

    /**************************************************
     * Constructor and private methods for constructor *
     **************************************************/
    public WisdomAboutPositions() {
        populateAllPositions();
        ratePositions();
    }

    private void populateAllPositions() {
        for (int i = 0; i <= MAX_OF_ROWS[0]; i++) {
            for (int j = 0; j <= MAX_OF_ROWS[1]; j++) {
                for (int k = 0; k <= MAX_OF_ROWS[2]; k++) {
                    for (int l = 0; l <= MAX_OF_ROWS[3]; l++) {
                        Position newPosition = new Position(i, j, k, l);
                        allPossiblePositions.add(newPosition);
                    }
                }
            }
        }
    }

    private void ratePositions() {
        setEquivalentPositionsLooser(new Position(0, 0, 0, 0)); //the looser take the last matchstick, and leaves this position on the table
        List<Position> winnerCandidateAllNextPositions;
        boolean repeatTheSearchAgain = true;
        while (repeatTheSearchAgain) {
            repeatTheSearchAgain = false;
            for (Position allPossiblePosition : allPossiblePositions) {
                if (allPossiblePosition.getRating() == Rating.UNKNOWN) { //checking UNKNOWN if they are winner
                    winnerCandidateAllNextPositions = allPossiblePosition.getPossibleNextPositions();
                    if (allOfTheseAreLooser(winnerCandidateAllNextPositions)) { //new winner found
                        setEquivalentPositionsWinner(allPossiblePosition);
                        allPossiblePosition.setRating(Rating.WINNER);
                        markPossiblePreviousPositionsAsLooser(allPossiblePosition);
                        repeatTheSearchAgain = true;
                        break; //New information, so the search has to start all over again
                    }
                }
            }

        }
    }

    private boolean allOfTheseAreLooser(List<Position> listForCheck) {
        for (Position actual : listForCheck) {
            if (isNotLooser(actual)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNotLooser(Position askedPosition) {
        boolean actualItemIsNotLooser;
        boolean positionsAreEquivalent;
        for (Position actualListItem : allPossiblePositions) {
            actualItemIsNotLooser = (actualListItem.getRating() != Rating.LOOSER);
            positionsAreEquivalent = actualListItem.isEquivalentPosition(askedPosition);
            if (actualItemIsNotLooser && positionsAreEquivalent) {
                return true;
            }
        }
        return false;
    }

    private void markPossiblePreviousPositionsAsLooser(Position winnerPosition) {
        int[] winnerFour = winnerPosition.getMatchsticksInRows();
        List<int[]> losers;
        losers = getPossiblePreviousPosition(winnerFour);
        for (int[] actual : losers) {
            setEquivalentPositionsLooser(new Position(actual[0], actual[1], actual[2], actual[3]));
        }
    }

    private List<int[]> getPossiblePreviousPosition(int[] actualPosition) {
        int[] previousPosition;
        List<int[]> result = new ArrayList<>();
        int actualRowMaxMatchsticks;

        actualRowMaxMatchsticks = MAX_OF_ROWS[0];
        if (actualPosition[0] < actualRowMaxMatchsticks) {

            for (int i = actualPosition[0] + 1; i <= actualRowMaxMatchsticks; i++) {
                previousPosition = new int[4];
                previousPosition[0] = i;
                previousPosition[1] = actualPosition[1];
                previousPosition[2] = actualPosition[2];
                previousPosition[3] = actualPosition[3];
                result.add(previousPosition);
            }
        }

        actualRowMaxMatchsticks = MAX_OF_ROWS[1];
        if (actualPosition[1] < actualRowMaxMatchsticks) {

            for (int i = actualPosition[1] + 1; i <= actualRowMaxMatchsticks; i++) {
                previousPosition = new int[4];
                previousPosition[0] = actualPosition[0];
                previousPosition[1] = i;
                previousPosition[2] = actualPosition[2];
                previousPosition[3] = actualPosition[3];
                result.add(previousPosition);
            }
        }

        actualRowMaxMatchsticks = MAX_OF_ROWS[2];
        if (actualPosition[2] < actualRowMaxMatchsticks) {

            for (int i = actualPosition[2] + 1; i <= actualRowMaxMatchsticks; i++) {
                previousPosition = new int[4];
                previousPosition[0] = actualPosition[0];
                previousPosition[1] = actualPosition[1];
                previousPosition[2] = i;
                previousPosition[3] = actualPosition[3];
                result.add(previousPosition);
            }
        }

        actualRowMaxMatchsticks = MAX_OF_ROWS[3];
        if (actualPosition[3] < actualRowMaxMatchsticks) {

            for (int i = actualPosition[3] + 1; i <= actualRowMaxMatchsticks; i++) {
                previousPosition = new int[4];
                previousPosition[0] = actualPosition[0];
                previousPosition[1] = actualPosition[1];
                previousPosition[2] = actualPosition[2];
                previousPosition[3] = i;
                result.add(previousPosition);
            }
        }

        return result;
    }

    private void setEquivalentPositionsWinner(Position positionToSet) {
        for (Position actual : allPossiblePositions) {
            if (actual.isEquivalentPosition(positionToSet)) {
                actual.setRating(Rating.WINNER);
            }
        }
    }

    private void setEquivalentPositionsLooser(Position positionToSet) {
        for (Position actual : allPossiblePositions) {
            if (actual.isEquivalentPosition(positionToSet)) {
                actual.setRating(Rating.LOOSER);
            }
        }
    }

    /***************************
     * methods for using wisdom *
     * *************************/

    private boolean isVisibleWinner(int[] thisFour, int level) {
        boolean actualListItemIsWinner;
        boolean actualListItemIsVisible;
        boolean positionsAreSame;
        for (Position actualListItem : allPossiblePositions) {
            actualListItemIsWinner = (actualListItem.getRating() == Rating.WINNER);
            actualListItemIsVisible = (actualListItem.getVisibleAboveLevel() <= level);
            positionsAreSame = Arrays.equals(thisFour, actualListItem.getMatchsticksInRows());
            if (actualListItemIsWinner && actualListItemIsVisible && positionsAreSame) {
                return true;
            }
        }
        return false;
    }

    public Position suggestNextStep(int gameLevel, Position actualPosition) {

        List<Position> possibleSteps = actualPosition.getPossibleNextPositions();

        //choose the winner step, if visible
        for (Position isItMaybe : possibleSteps) {
            if (isVisibleWinner(isItMaybe.getMatchsticksInRows(), gameLevel)) {
                return isItMaybe;
            }
        }
        //if not, choose random step, complexity depending on gameLevel
        possibleSteps.sort(new SortPositionsByComplexity());
        int oneThird = possibleSteps.size() / 3;
        int minIndex = 0;
        int maxIndex = possibleSteps.size() - 1;

        switch (gameLevel) {
            case 0, 1 -> maxIndex = oneThird;
            case 2, 3 -> {
                minIndex = oneThird;
                maxIndex = maxIndex - oneThird;
            }
            case 4, 5 -> minIndex = oneThird;
            default -> minIndex = maxIndex - oneThird;
        }

        if (minIndex == maxIndex) {
            return possibleSteps.get(maxIndex);
        }


        int index = random.nextInt(minIndex, maxIndex);
        return possibleSteps.get(index);
    }
    /***************************
     * getter for testing only *
     * *************************/

    public List<Position> getRatedList(){
        List<Position> result = new ArrayList<>();
        allPossiblePositions.stream()
                .forEach(p -> result.add(p));
        return result;
    }
}