package com.aaronco;

import GameOfLife.Board;
import GameOfLife.BoardUtils;
import GameOfLife.WrappedBoard;
import Genetic.*;
import Genetic.Bitset.BitsetCrossoverSinglePoint;
import Genetic.Bitset.BitsetGeneticAlgorithm;
import Genetic.Bitset.BitsetSample;

import java.io.IOException;
import java.util.*;

public class Main {

    public static Board runBoard(Board board, BitSet initial, int bitsetLengthSqrt, int runs){
        BoardUtils.populateBoardWithBitset(board, initial, bitsetLengthSqrt, bitsetLengthSqrt);

        for (int i = 0; i < runs; i++) {
            board = board.getNext();
        }

        return board;
    }

    public static void main(String[] args) {
        Board gb =  new GameOfLife.WrappedBoard(128,128);

        int dnaSqrtLength = 5;
        int dnaLength = dnaSqrtLength * dnaSqrtLength;
        int dnaCrossoverPoint = (dnaLength * 10) / 6;
        int samplesPerGenerationr = 20;
        int runsPerSimulation = 100;
        int numberOfRuns = 100;
        //CrossoverMethod<BitSet> crossoverMethod = new BitsetCrossoverRandom(new Random(), 0.4f, dnaLength);
        CrossoverMethod<BitSet> crossoverMethod =  new BitsetCrossoverSinglePoint(dnaCrossoverPoint);
        //CrossoverMethod<BitSet> crossoverMethod =  new BitsetCrossoverDoublePoint(dnaCrossoverPoint, dnaCrossoverPoint+5);

        GeneticOptimization optimizer = new BitsetGeneticAlgorithm(dnaLength, samplesPerGenerationr, new SampleEvaluator() {
            @Override
            public BitsetSample evaluate(Sample sample) {
                return evaluate((BitsetSample)sample);
            }

            public BitsetSample evaluate(BitsetSample sample) {
                sample.score = Main.runBoard(new GameOfLife.WrappedBoard(gb.getWidth(), gb.getHeight()), sample.value, dnaSqrtLength, runsPerSimulation).aliveCellCount();
                return sample;
            }
        }, crossoverMethod);

        optimizer.createInitialPopulation();

        BitsetSample[] bestSamples = new BitsetSample[numberOfRuns+1];

        System.out.println("Best Initial Sample: "+optimizer.bestSample());

        bestSamples[0] = (BitsetSample)optimizer.bestSample();

        for(int i = 1; i < numberOfRuns+1; ++i){
            optimizer.generateNewPopulation();
            System.out.println("Best Sample At: "+i+"\t"+optimizer.bestSample());
            bestSamples[i] = (BitsetSample)optimizer.bestSample();
        }


        for(int i = 0; i < numberOfRuns+1; ++i){
            if(i == 0 || bestSamples[i].compareScore(bestSamples[i-1]) != 0) {
                try {
                    String filename = "Run" + i + "_" + bestSamples[i].score + ".gif";
                    System.out.println("Generating GIF "+filename);
                    BoardUtils.makeGifFromRun(filename, new WrappedBoard(gb.getWidth(), gb.getHeight()), runsPerSimulation, bestSamples[i].value, dnaSqrtLength, dnaSqrtLength);
                }catch (IOException ioe){
                    System.out.println(ioe);
                }
            }
        }

        System.out.println("DONE");
    }

}
