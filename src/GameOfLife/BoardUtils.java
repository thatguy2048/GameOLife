package GameOfLife;

import utils.GifSequenceWriter;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class BoardUtils {
    public static Board populateBoardWithBitset(Board board, BitSet bitset, int bitsetWidth, int bitsetHeight){
        Board output = null;
        if(board.getWidth() < bitsetWidth || board.getHeight() < bitsetHeight){

        }else{
            int i_start = board.getWidth()/2 - bitsetWidth/2;
            int j_start = board.getHeight()/2 - bitsetHeight/2;
            for (int i = 0; i < bitsetWidth; i++) {
                for (int j = 0; j < bitsetHeight; j++) {
                    board.setCell(i+i_start, j+j_start, bitset.get(i*bitsetWidth+j));
                }
            }
            output = board;
        }
        return output;
    }

    public static BufferedImage boardToImage(final Board board){
        BufferedImage output = new BufferedImage(board.getWidth(), board.getHeight(), BufferedImage.TYPE_USHORT_555_RGB);

        for(int w = 0; w < board.getWidth(); ++w){
            for(int h = 0; h < board.getHeight(); ++h){
                if(board.getCell(w,h)) {
                    output.setRGB(w, h, 255);
                }
            }
        }
        return output;
    }

    public static Board makeGifFromRun(String filename, Board board, int runs, BitSet initial, int bitsetWidth, int bitsetHeight) throws IOException{
        //initialize board
        populateBoardWithBitset(board, initial, bitsetWidth, bitsetHeight);

        ImageOutputStream ios = new FileImageOutputStream(new File(filename));
        GifSequenceWriter gsw = new GifSequenceWriter(ios, BufferedImage.TYPE_USHORT_555_RGB, 50, false);

        gsw.writeToSequence(BoardUtils.boardToImage(board));

        for (int i = 0; i < runs; i++) {
            board = board.getNext();
            gsw.writeToSequence(BoardUtils.boardToImage(board));
        }

        gsw.close();
        ios.close();

        return board;
    }
}
