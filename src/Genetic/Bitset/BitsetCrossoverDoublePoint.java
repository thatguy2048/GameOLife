package Genetic.Bitset;

import java.util.BitSet;

public class BitsetCrossoverDoublePoint extends BitsetCrossoverSinglePoint {
    int crossPointB;
    public BitsetCrossoverDoublePoint(int crossPointA, int crossPointB) {
        super(crossPointA);
        this.crossPointB = crossPointB;
    }

    @Override
    public BitSet crossover(BitSet a, BitSet b) {
        BitSet output = (BitSet)a.clone();
        for(int i = crossPoint; i <= crossPointB; ++i){
            output.set(i, b.get(i));
        }
        return output;
    }
}
