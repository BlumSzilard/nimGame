package adaptiveNim;

import java.util.ArrayList;
import java.util.List;

public class WisdomAboutPositions {
    private List<Position> allPositions = new ArrayList<>();
    private final static int[] MAX_OF_ROWS = {1,3,5,7};

    public WisdomAboutPositions() {
        populateAllPositions();
        setPositionLooser(new int[]{0,0,0,0}); //the looser take the last matchstick, and leaves this position on the table
    //    ratePositions();
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
