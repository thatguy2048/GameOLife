package com.aaronco;

import GameOfLife.Board;
import GameOfLife.BoardUtils;
import GameOfLife.WrappedBoard;
import Genetic.BitsetGeneticAlgorithm;
import Genetic.Sample;
import Genetic.SampleConsumer;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void makeGifFromRun(String filename, Board board, int runs, BitSet initial, int bitsetLengthSqrt){
        System.out.println("Generating GIF "+filename);

        //initialize board
        for(int i = 0; i < bitsetLengthSqrt; ++i){
            for(int j = 0; j < bitsetLengthSqrt; ++j){
                board.setCell(i,j,initial.get(j+i*bitsetLengthSqrt));
            }
        }

        ImageOutputStream ios = null;
        GifSequenceWriter gsw = null;
        try {
            ios = new FileImageOutputStream(new File(filename));
            gsw = new GifSequenceWriter(ios, BufferedImage.TYPE_USHORT_555_RGB, 50, false);

            gsw.writeToSequence(BoardUtils.boardToImage(board));

            for (int i = 0; i < runs; i++) {
                System.out.println("Run "+i);
                board = board.getNext();
                gsw.writeToSequence(BoardUtils.boardToImage(board));
            }

            gsw.close();
            ios.close();
        }catch (IOException ioe){
            System.out.println("Failed to create output image");
        }
    }

    void runBoardsAndPrint(){
        int iterations = 2500;
        GameOfLife.Board[] boards = new GameOfLife.Board[iterations];

        boards[0] = new GameOfLife.WrappedBoard(128,128);

        for(int i = 1; i < boards[0].getHeight()-1; ++i){
            boards[0].setCell(boards[0].getWidth()/2, i, true);
        }


        System.out.println("Time to compute "+iterations+" iterations on board of size "+boards[0].getWidth()+","+boards[0].getHeight()+"\t"+boards[0].getWidth()*boards[0].getHeight()+" cells.");
        long startTime = System.currentTimeMillis();

        for (int i = 1; i < iterations; ++i) {
            boards[i] = boards[i-1].getNext();
            if(i%10 == 0){
                System.out.println(i+" of "+iterations);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Time in milliseconds: "+(endTime-startTime));

        System.out.println("Create GIF");

        ImageOutputStream ios = null;
        GifSequenceWriter gsw = null;
        try {
            ios = new FileImageOutputStream(new File("Test.gif"));
            gsw = new GifSequenceWriter(ios, BufferedImage.TYPE_USHORT_555_RGB, 50, false);

            for (int i = 0; i < iterations; ++i) {
                gsw.writeToSequence(BoardUtils.boardToImage(boards[i]));
                if(i%10 == 0){
                    System.out.println(i+" of "+iterations);
                }
            }

            gsw.close();
            ios.close();
        }catch (IOException ioe){
            System.out.println("Failed to create output image");
        }
    }

    public static Board runBoard(Board board, BitSet initial, int bitsetLengthSqrt, int runs){
        for(int i = 0; i < bitsetLengthSqrt; ++i){
            for(int j = 0; j < bitsetLengthSqrt; ++j){
                board.setCell(i,j,initial.get(j+i*bitsetLengthSqrt));
            }
        }

        for (int i = 0; i < runs; i++) {
            board = board.getNext();
        }

        return board;
    }

    public static void main(String[] args) {
        Board gb =  new GameOfLife.WrappedBoard(128,128);

        int dnaSqrtLength = 5;
        int dnaLength = dnaSqrtLength * dnaSqrtLength;
        int startingSamples = 10;
        int runsPerSimulation = 100;
        int numberOfRuns = 100;

        Genetic.BitsetGeneticAlgorithm bga = new BitsetGeneticAlgorithm(dnaLength, startingSamples, new SampleConsumer() {
            @Override
            public Genetic.Sample consume(Genetic.Sample sample) {
                sample.score = Main.runBoard(gb, sample.value, dnaSqrtLength, runsPerSimulation).aliveCellCount();
                return sample;
            }
        });

        bga.createInitialPopulation();

        Sample[] bestSamples = new Sample[numberOfRuns+1];

        System.out.println("Best Initial Sample: "+bga.bestSample());

        bestSamples[0] = bga.bestSample();

        for(int i = 1; i < numberOfRuns+1; ++i){
            bga.generateNewPopulation();
            System.out.println("Best Sample At: "+i+"\t"+bga.bestSample());
            bestSamples[i] = bga.bestSample();
        }


        for(int i = 0; i < numberOfRuns+1; ++i){
            if(i == 0 || bestSamples[i] != bestSamples[i-1]) {
                makeGifFromRun("Run" + i + "_"+bestSamples[i].score+".gif", new WrappedBoard(gb.getWidth(),gb.getHeight()), runsPerSimulation, bestSamples[i].value, dnaSqrtLength);
            }
        }

        System.out.println("DONE");
    }

}
