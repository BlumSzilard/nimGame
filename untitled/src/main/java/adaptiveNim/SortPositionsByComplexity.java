package adaptiveNim;

import java.util.Comparator;

public class SortPositionsByComplexity implements Comparator<Position> {
    public int compare(Position a, Position b){
        return a.getComplexity() - b.getComplexity();
    }
}
