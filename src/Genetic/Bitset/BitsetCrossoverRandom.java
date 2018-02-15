package Genetic.Bitset;

import Genetic.CrossoverMethod;

import java.util.BitSet;
import java.util.Random;

public class BitsetCrossoverRandom extends CrossoverMethod<BitSet> {
    protected Random ranom;
    protected float bChance;
    protected int bitsetLength;

    public BitsetCrossoverRandom(Random ranom, float bChance, int bitsetLength) {
        this.ranom = ranom;
        this.bChance = bChance;
        this.bitsetLength = bitsetLength;
    }

    @Override
    public BitSet crossover(BitSet a, BitSet b) {
        BitSet output = (BitSet)a.clone();
        for(int i = 0; i < bitsetLength; ++i){
            if(ranom.nextFloat() <= bChance){
                output.set(i, b.get(i));
            }
        }
        return output;
    }
}
