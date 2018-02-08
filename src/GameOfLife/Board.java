package GameOfLife;

import java.util.Arrays;
import java.util.BitSet;

public class Board {
    protected BitSet[] cells;
    protected int width;
    protected int height;
    protected int widthm1;
    protected int heightm1;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        widthm1 = width-1;
        heightm1 = height-1;

        cells = new BitSet[height];
        for(int h = 0; h < height; ++h){
            cells[h] = new BitSet(width);
        }
    }

    public Board(final Board other) {
        this.width = other.getWidth();
        this.height = other.getHeight();

        widthm1 = width-1;
        heightm1 = height-1;

        cells = new BitSet[this.height];
        for(int h = 0; h < height; ++h){
            cells[h] = (BitSet)(other.getCells()[h].clone());
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public final BitSet[] getCells() {
        return cells;
    }

    public boolean getCell(int w, int h){
        return cells[h].get(w);
    }

    public void setCell(int w, int h, boolean value){
        cells[h].set(w, value);
    }

    public int countNeighbors(int w, int h){
        int output = 0;
        if(w > 0 && cells[h].get(w-1)) ++output;
        if(w < widthm1 && cells[h].get(w+1)) ++output;
        if(h > 0 && cells[h-1].get(w)) ++output;
        if(h < heightm1 && cells[h+1].get(w)) ++output;
        if(w > 0 && h > 0 && cells[h-1].get(w-1)) ++output;
        if(w < widthm1 && h > 0 && cells[h-1].get(w+1)) ++output;
        if(w > 0 && h < heightm1 &&cells[h+1].get(w-1)) ++output;
        if(w < widthm1 && h < heightm1 && cells[h+1].get(w+1)) ++output;
        return output;
    }

    public boolean aliveNextRound(int w, int h){
        int _count = countNeighbors(w,h);
        return _count == 3 || (_count == 2 && cells[h].get(w));
    }

    public Board populateNextBoard(Board board){
        for(int h = 0; h < height; ++h){
            for(int w = 0; w < height; ++w){
                board.setCell(w, h, aliveNextRound(w, h));
            }
        }
        return board;
    }

    public Board getNext(){
        return populateNextBoard(new Board(this));
    }

    public int aliveCellCount(){
        int output = 0;
        for(int h = 0; h < height; ++h){
            output += cells[h].cardinality();
        }
        return output;
    }

    @Override
    public String toString() {
        String output = "Board "+this.width+","+this.height+" alive: "+aliveCellCount();
        for(int h = 0; h < this.height; ++h){
            String line = "";
            for(int w = 0; w < this.width; ++w){
                line += cells[h].get(w)?'1':'0';
            }
            output += '\n' + line;
        }
        return output;
    }
}
