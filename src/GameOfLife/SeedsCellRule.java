package GameOfLife;

public class SeedsCellRule implements CellLivingRule {
    int livingCount;

    public SeedsCellRule(int livingCount){
        this.livingCount = livingCount;
    }

    public SeedsCellRule(){
        this(2);
    }

    @Override
    public boolean aliveNextRound(Board board, int cellx, int celly) {
        return board.countNeighbors(cellx, celly) == 2;
    }
}
