package com.aaronco;

import GameOfLife.BoradUtils;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int iterations = 2500;
        GameOfLife.Board[] boards = new GameOfLife.Board[iterations];

        boards[0] = new GameOfLife.WrappedBoard(256,256);

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
                gsw.writeToSequence(BoradUtils.boardToImage(boards[i]));
                if(i%10 == 0){
                    System.out.println(i+" of "+iterations);
                }
            }

            gsw.close();
            ios.close();
        }catch (IOException ioe){
            System.out.println("Failed to create output image");
        }

        System.out.println("DONE");
    }
}
