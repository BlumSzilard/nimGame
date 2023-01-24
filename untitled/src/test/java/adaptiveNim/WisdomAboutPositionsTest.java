package adaptiveNim;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WisdomAboutPositionsTest {
    WisdomAboutPositions wisdomAboutPositions;
    @Test
    void testIfAllPositionsAreRightRated(){
        wisdomAboutPositions = new WisdomAboutPositions();
        List<Position> allPositions = wisdomAboutPositions.getRatedList();

        assertEquals(10000,allPositions.size());

        for (Position actual:allPositions){
            int[] actualFour =actual.getMatchsticksInRows();
            int max1 = Math.max(actualFour[0], actualFour[1]);
            int max2 =Math.max(actualFour[2], actualFour[3]);
            int max = Math.max(max1,max2);
            assertNotEquals(Rating.UNKNOWN,actual.getRating());
            if (actual.getRating() == Rating.WINNER){


                if (max > 1){
                assertEquals(0,actualFour[0] ^ actualFour[1] ^ actualFour[2] ^ actualFour[3]);
                    //see "nim-sum", "bitwise xor" at https://en.wikipedia.org/wiki/Nim
                    }
            } else {

                if (max> 1 ){
                assertNotEquals(0,actualFour[0] ^ actualFour[1] ^ actualFour[2] ^ actualFour[3]);
                    //see "nim-sum", "bitwise xor" at https://en.wikipedia.org/wiki/Nim
                }
            }
        }
    }

    @Test
    void suggestNextStepIfVisibleTheWinner(){
        wisdomAboutPositions = new WisdomAboutPositions();
        Position actualPosition = new Position(0,0,2,0);
        Position suggestedPosition = wisdomAboutPositions.suggestNextStep(100,actualPosition);
        int[] expected = {0,0,1,0};

        assertArrayEquals(expected, suggestedPosition.getMatchsticksInRows());

        actualPosition = new Position(2,3,5,7);
        expected = new int[]{1, 3, 5, 7};
        suggestedPosition = wisdomAboutPositions.suggestNextStep(100,actualPosition);

        assertArrayEquals(expected, suggestedPosition.getMatchsticksInRows());

        actualPosition = new Position(1,3,2,7);
        expected = new int[]{1, 3, 2, 0};
        suggestedPosition = wisdomAboutPositions.suggestNextStep(100,actualPosition);

        assertArrayEquals(expected, suggestedPosition.getMatchsticksInRows());
    }
}