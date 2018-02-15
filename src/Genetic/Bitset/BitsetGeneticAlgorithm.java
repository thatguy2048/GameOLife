package Genetic.Bitset;

import Genetic.CrossoverMethod;
import Genetic.GeneticOptimization;
import Genetic.Sample;
import Genetic.SampleEvaluator;

import java.util.BitSet;

public class BitsetGeneticAlgorithm extends GeneticOptimization {
    int dnaSize;

    public BitsetGeneticAlgorithm(int dnaSize, int samplesPerGeneration, SampleEvaluator sampleEvaluator, CrossoverMethod<BitSet> crossoverMethod){
        super(samplesPerGeneration, sampleEvaluator, crossoverMethod);
        this.dnaSize = dnaSize;
    }

    @Override
    protected Sample createNewSample() {
        return new BitsetSample(dnaSize);
    }
};