package GameOfLife;

public class WrappedBoard extends Board {
    public WrappedBoard(int width, int height) {
        super(width, height);
    }

    public WrappedBoard(Board other) {
        super(other);
    }

    @Override
    public int countNeighbors(int w, int h) {
        int output = 0;

        int _left = (w > 0)?w-1:widthm1;
        int _right = (w < widthm1)?w+1:0;
        int _top = (h > 0)?h-1:heightm1;
        int _bottom = (h < heightm1)?h+1:0;

        if(cells[h].get(_left)) ++output;
        if(cells[h].get(_right)) ++output;
        if(cells[_top].get(w)) ++output;
        if(cells[_bottom].get(w)) ++output;
        if(cells[_top].get(_left)) ++output;
        if(cells[_top].get(_right)) ++output;
        if(cells[_bottom].get(_left)) ++output;
        if(cells[_bottom].get(_right)) ++output;
        return output;
    }

    @Override
    public Board getNext() {
        return populateNextBoard(new WrappedBoard(this));
    }

    @Override
    public String toString() {
        return "Wrapped"+super.toString();
    }
}
