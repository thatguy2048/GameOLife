package Genetic.Bitset;

import Genetic.CrossoverMethod;
import Genetic.Sample;

import java.util.BitSet;
import java.util.Random;

public class BitsetSample implements Sample {
    public BitSet value;
    public int score;
    protected int length;


    public BitsetSample(BitSet value, int length){
        this.value = (BitSet)value.clone();
        this.length = length;
    }

    public BitsetSample(int length){
        this(new BitSet(length), length);
    }

    public BitsetSample(BitsetSample other){
        this(other.value, other.getLength());
    }

    @Override
    public BitsetSample clone() {
        return new BitsetSample(this);
    }

    public int getLength(){
        return length;
    }

    public boolean compareValue(BitsetSample other){
        return value.equals(other.value);
    }

    @Override
    public boolean compareValue(Sample other) {
        return this.compareValue((BitsetSample)other);
    }

    public int compareScore(BitsetSample other){
        return Integer.compare(score, other.score);
    }

    @Override
    public int compareScore(Sample other) {
        return this.compareScore((BitsetSample)other);
    }

    public BitsetSample mate(BitsetSample sample, CrossoverMethod<BitSet> crossoverMethod){
        return new BitsetSample(crossoverMethod.crossover(this.value, sample.value), this.length);
    }

    @Override
    public BitsetSample mate(Sample other, CrossoverMethod crossoverMethod) {
        return this.mate((BitsetSample)other, crossoverMethod);
    }

    public BitsetSample randomize(Random random) {
        return mutate(random, 0.5f);
    }

    public BitsetSample mutate(Random rand, float mutateChance) {
        for(int i = 0; i < length; ++i){
            if(rand.nextFloat() < mutateChance){
                value.flip(i);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(score);
        sb.append('\t');
        for (int i = 0; i < value.size(); i++) {
            sb.append(value.get(i)?'1':'0');
        }
        return sb.toString();
    }
};
