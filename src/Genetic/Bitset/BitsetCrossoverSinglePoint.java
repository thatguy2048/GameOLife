package Genetic.Bitset;

import Genetic.CrossoverMethod;

import java.util.BitSet;

public class BitsetCrossoverSinglePoint extends CrossoverMethod<BitSet> {
    protected int crossPoint;

    public BitsetCrossoverSinglePoint(int crossPoint){
        this.crossPoint = crossPoint;
    }

    @Override
    public BitSet crossover(BitSet a, BitSet b) {
        BitSet output = (BitSet)b.clone();
        for(int i = 0; i < crossPoint; ++i){
            output.set(i, a.get(i));
        }
        return output;
    }
}
