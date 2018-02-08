package GameOfLife;

import java.awt.image.BufferedImage;

public class BoradUtils {
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
}
