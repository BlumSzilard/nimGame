package adaptiveNim;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortPositionsByComplexityTest {

    @Test
    void testSorting() {
        List<Position> positions = new ArrayList<>();
        Position position;
        position = new Position(1, 0, 0, 0);
        positions.add(position);
        position = new Position(1, 1, 1, 1);
        positions.add(position);
        position = new Position(3, 3, 3, 3);
        positions.add(position);
        position = new Position(2, 2, 2, 2);
        positions.add(position);
        SortPositionsByComplexity sortPositionsByComplexity = new SortPositionsByComplexity();
        Collections.sort(positions, sortPositionsByComplexity);

        assertEquals(3, positions.get(3).getMatchsticksInRow(1));
        assertEquals(0, positions.get(0).getMatchsticksInRow(2));
    }
}