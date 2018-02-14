package Genetic;

import java.util.BitSet;
import java.util.Random;

public class BitsetSample extends Sample<BitSet, Integer>{
    protected int length;

    public BitsetSample(BitSet value, int length){
        this.value = (BitSet)value.clone();
        this.length = length;
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

    public BitsetSample mate(BitsetSample other, float dividePercentage){
        BitsetSample output = new BitsetSample(other);
        dividePercentage *= length;
        for(int i = 0; i < dividePercentage; ++i){
            output.value.set(i, value.get(i));
        }
        return output;
    }

    @Override
    public BitsetSample randomize(Random random) {
        return mutate(random, 0.5f);
    }

    @Override
    public BitsetSample mate(Sample other, float dividePercentage) {
        return this.mate((BitsetSample)other, dividePercentage);
    }

    @Override
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
