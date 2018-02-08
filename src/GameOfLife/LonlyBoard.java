package GameOfLife;

import java.util.BitSet;
import java.util.LinkedList;

//Much slower, but more memory efficient than the normal board.
public class LonlyBoard {
    protected LinkedList<BitSet> cells;
    protected int width;
    protected int height;
    protected int widthm1;
    protected int heightm1;

    public LonlyBoard(int width, int height) {
        setSize(width, height);

        cells = new LinkedList<BitSet>();
        for(int h = 0; h < height; ++h){
            cells.add(new BitSet(width));
        }
    }

    protected void setSize(int width, int height){
        this.width = width;
        this.height = height;

        this.widthm1 = width-1;
        this.heightm1 = height-1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCell(int w, int h, boolean value){
        cells.get(h).set(w, value);
    }

    public boolean getCell(int w, int h){
        return cells.get(h).get(w);
    }

    public int countNeighbors(int w, int h){
        int output = 0;
        if(w > 0 && getCell(w-1,h)) ++output;
        if(w < widthm1 && getCell(w+1,h)) ++output;
        if(h > 0 && getCell(w,h-1)) ++output;
        if(h < heightm1 && getCell(w,h+1)) ++output;
        if(w > 0 && h > 0 && getCell(w-1,h-1)) ++output;
        if(w < widthm1 && h > 0 && getCell(w+1,h-1)) ++output;
        if(w > 0 && h < heightm1 && getCell(w-1,h+1)) ++output;
        if(w < widthm1 && h < heightm1 && getCell(w+1,h+1)) ++output;
        return output;
    }

    public boolean aliveNextRound(int w, int h){
        int _count = countNeighbors(w,h);
        return _count == 3 || (_count == 2 && cells.get(h).get(w));
    }

    public void updateNextRound(){
        LinkedList<BitSet> nextCells = new LinkedList<>();

        for(int h = 0; h < height; ++h) {
            nextCells.add(new BitSet(width));
            for(int w = 0; w < this.width; ++w){
                if(aliveNextRound(w,h)){
                    nextCells.get(h).set(w);
                }
            }
            if(h > 1){
                cells.set(h-2, null);
            }
        }

        this.cells = nextCells;
        this.heightm1 = height - 1;
    }

    public int aliveCellCount(){
        int output = 0;
        for(int h = 0; h < height; ++h){
            output += cells.get(h).cardinality();
        }
        return output;
    }

    @Override
    public String toString() {
        String output = "LonlyBoard "+this.width+","+this.height+" alive: "+aliveCellCount();
        for(int h = 0; h < this.height; ++h){
            String line = "";
            for(int w = 0; w < this.width; ++w){
                line += cells.get(h).get(w)?'1':'0';
            }
            output += '\n' + line;
        }
        return output;
    }
}
