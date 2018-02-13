package Genetic;

import java.util.BitSet;
import java.util.Random;

public class Sample{
    public int score = -1;
    public BitSet value = null;

    public Sample(BitSet value){
        this.value = (BitSet)value.clone();
    }

    public Sample(Sample other){
        this(other.value);
    }

    public Sample clone(){
        return new Sample(this);
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

    public static Sample Mate(Sample a, Sample b, int a_length){
        Sample output = new Sample(b);
        for(int i = 0; i < a_length; ++i){
            output.value.set(i, b.value.get(i));
        }
        return output;
    }

    public static Sample Mutate(Sample s, Random random, float mutateChancePerBit, int bitLength){
        for(int i = 0; i < bitLength; ++i){
            if(random.nextFloat() < mutateChancePerBit){
                s.value.flip(i);
            }
        }
        return s;
    }
};
