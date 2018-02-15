package Genetic;

import java.util.Random;

public interface Sample {
    boolean compareValue(Sample other);
    int compareScore(Sample other);
    Sample clone();
    Sample randomize(Random random);
    Sample mutate(Random rand, float mutateChance);
    Sample mate(Sample other, CrossoverMethod crossoverMethod);
}
