package adaptiveNim;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    Position position;

    @org.junit.jupiter.api.Test
    void testGetMatchsticksInRows() {
        position = new Position(1,2,3,4);

        assertEquals(1,position.getMatchsticksInRows()[0]);
        assertArrayEquals(new int[] {1,2,3,4},position.getMatchsticksInRows());
         }
    @org.junit.jupiter.api.Test
    void testGetMatchsticksInRowsWithZeros() {
        position = new Position(0,0,0,0);

        assertEquals(0,position.getMatchsticksInRows()[0]);
        assertArrayEquals(new int[] {0,0,0,0},position.getMatchsticksInRows());
    }

    @org.junit.jupiter.api.Test
    void testGetRatingDefault() {
        position = new Position(1,2,3,4);

        assertEquals(Rating.UNKNOWN,position.getRating());
    }

    @org.junit.jupiter.api.Test
    void testSetRating() {
        position = new Position(1,2,3,4);

        assertEquals(Rating.UNKNOWN,position.getRating());

        position.setRating(Rating.VINNER);
        assertEquals(Rating.VINNER,position.getRating());

        position.setRating(Rating.LOOSER);
        assertEquals(Rating.LOOSER, position.getRating());
    }

    @org.junit.jupiter.api.Test
    void testGetMatchsticksInRow() {
        position = new Position(1,2,3,4);

        assertEquals(1,position.getMatchsticksInRow(0));
        assertEquals(2,position.getMatchsticksInRow(1));
        assertEquals(3,position.getMatchsticksInRow(2));
        assertEquals(4,position.getMatchsticksInRow(3));
    }

    @org.junit.jupiter.api.Test
    void getPositionOrdered() {
        position = new Position(4,3,1,7);
        assertArrayEquals(new int[] {1,3,4,7}, position.getMatchsticksInRowsAsc());

        position = new Position(1,1,2,1);
        assertArrayEquals(new int[] {1,1,1,2}, position.getMatchsticksInRowsAsc());

        position = new Position(1,0,0,0);
        assertArrayEquals(new int[] {0,0,0,1}, position.getMatchsticksInRowsAsc());

        position = new Position(0,0,0,0);
        assertArrayEquals(new int[] {0,0,0,0}, position.getMatchsticksInRowsAsc());

        position = new Position(1,1,1,1);
        assertArrayEquals(new int[] {1,1,1,1}, position.getMatchsticksInRowsAsc());

        position = new Position(1,2,3,4);
        assertArrayEquals(new int[] {1,2,3,4}, position.getMatchsticksInRowsAsc());
    }


    @org.junit.jupiter.api.Test
    void testIsEquivalent() {
        position = new Position(1,2,1,2);
        assertTrue(position.isEquivalentPosition(new Position(2,1,1,2)));
        assertFalse(position.isEquivalentPosition(new Position(1,1,1,2)));

        position = new Position(1,0,0,0);
        assertTrue(position.isEquivalentPosition(new Position(0,0,0,1)));
        assertFalse(position.isEquivalentPosition(new Position (0,0,0,0)));

    }

    @org.junit.jupiter.api.Test
    void testGetPossibleNextPositions() {
        position = new Position(1,0,0,0);
        List<Position> result = position.getPossibleNextPositions();

        assertTrue(result.size() > 0);

        int[] memberOfExpected = {0,0,0,0};
        assertArrayEquals(memberOfExpected, result.get(0).getMatchsticksInRows());

        memberOfExpected = new int[] {1,0,0,0};
        assertFalse(Arrays.equals(memberOfExpected,result.get(0).getMatchsticksInRows()));

        position = new Position(1,2,2,0);
        result = position.getPossibleNextPositions();

        assertTrue(result.size() > 4);

        memberOfExpected = new int[] {1,2,0,0};
        assertArrayEquals(memberOfExpected,result.get(3).getMatchsticksInRows());

        memberOfExpected = new int[] {0,2,2,0};
        assertArrayEquals(memberOfExpected, result.get(0).getMatchsticksInRows());

        position = new Position(0,0,2,0);
        result = position.getPossibleNextPositions();
        memberOfExpected = new int[] {0,0,1,0};
        assertArrayEquals(memberOfExpected,result.get(1).getMatchsticksInRows());
    }

    @org.junit.jupiter.api.Test
    void testSetLevel2and3(){
        position = new Position(1,1,1,0);
        position.setRating(Rating.VINNER);

        assertEquals(2, position.getRatingIsVisibleAbove());

        position = new Position(0,1,1,1);
        position.setRating(Rating.VINNER);

        assertEquals(2, position.getRatingIsVisibleAbove());

        position = new Position(1,1,0,1);
        position.setRating(Rating.VINNER);

        assertEquals(2, position.getRatingIsVisibleAbove());

        position = new Position(2,2,0,0);
        position.setRating(Rating.VINNER);

        assertEquals(3, position.getRatingIsVisibleAbove());

        position = new Position(0,2,0,2);
        position.setRating(Rating.VINNER);

        assertEquals(3, position.getRatingIsVisibleAbove());
    }
    @org.junit.jupiter.api.Test
    void testSetLevel4and5(){
        position = new Position(3,3,0,0);
        position.setRating(Rating.VINNER);

        assertEquals(4, position.getRatingIsVisibleAbove());

        position = new Position(5,0,0,5);
        position.setRating(Rating.VINNER);

        assertEquals(4, position.getRatingIsVisibleAbove());

        position = new Position(0,6,0,6);
        position.setRating(Rating.VINNER);

        assertEquals(4, position.getRatingIsVisibleAbove());

        position = new Position(1,6,1,6);
        position.setRating(Rating.VINNER);

        assertEquals(5, position.getRatingIsVisibleAbove());

        position = new Position(1,7,1,7);
        position.setRating(Rating.VINNER);

        assertEquals(5, position.getRatingIsVisibleAbove());
    }

    @org.junit.jupiter.api.Test
    void testSetLevel6_7and8(){
        position = new Position(1,3,0,2);
        position.setRating(Rating.VINNER);

        assertEquals(6, position.getRatingIsVisibleAbove());

        position = new Position(1,6,4,3);
        position.setRating(Rating.VINNER);

        assertEquals(7, position.getRatingIsVisibleAbove());

        position = new Position(6,5,5,6);
        position.setRating(Rating.VINNER);

        assertEquals(8, position.getRatingIsVisibleAbove());
    }

    @org.junit.jupiter.api.Test
    void testGetComplexity(){
        position = new Position(1,3,0,2);
        assertEquals(24,position.getComplexity());
        assertEquals(24,position.getComplexity()); //testing if cached

        position = new Position(0,0,0,0);
        assertEquals(1,position.getComplexity());

        position = new Position(5,5,5,5);
        assertEquals(1296,position.getComplexity());
    }

    @org.junit.jupiter.api.Test
    void testIsSamePosition(){
        position = new Position(1,3,0,2);
        Position samePosition = new Position(1,3,0,2);
        Position onlyEquivalentPosition = new Position(0,1,2,3);
        assertTrue(position.isSamePosition(samePosition));
        assertFalse(position.isSamePosition(onlyEquivalentPosition));
    }

}