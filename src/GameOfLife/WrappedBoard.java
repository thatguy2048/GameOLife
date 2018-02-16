package GameOfLife;

import java.util.BitSet;

public class WrappedBoard extends Board {
    public WrappedBoard(int width, int height, CellLivingRule livingRule) {
        super(width, height, livingRule);
    }

    public WrappedBoard(Board other) {
        super(other);
    }

    @Override
    public boolean[] getNeighbors(int w, int h) {
        boolean[] output = new boolean[8];
        int _left = (w > 0)?w-1:widthm1;
        int _right = (w < widthm1)?w+1:0;
        int _top = (h > 0)?h-1:heightm1;
        int _bottom = (h < heightm1)?h+1:0;

        output[0] = cells[_top].get(_left);
        output[1] = cells[_top].get(w);
        output[2] = cells[_top].get(_right);
        output[3] = cells[h].get(_left);
        output[4] = cells[h].get(_right);
        output[5] = cells[_bottom].get(_left);
        output[6] = cells[_bottom].get(w);
        output[7] = cells[_bottom].get(_right);

        return output;
    }

    @Override
    public WrappedBoard clone() {
        return new WrappedBoard(this);
    }

    @Override
    public String toString() {
        return "Wrapped"+super.toString();
    }
}
