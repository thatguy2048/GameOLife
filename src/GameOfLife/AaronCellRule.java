package GameOfLife;

public class AaronCellRule implements CellLivingRule {
    @Override
    public boolean aliveNextRound(Board board, int cellx, int celly) {
        boolean[] neighbors = board.getNeighbors(cellx, celly);
        int _count = board.countNeighbors(cellx, celly);
        return  _count < 6 && (neighbors[0] && neighbors[1] && neighbors[2] ||
                neighbors[1] && neighbors[2] && neighbors[4] ||
                neighbors[2] && neighbors[4] && neighbors[7] ||
                neighbors[4] && neighbors[7] && neighbors[6] ||
                neighbors[7] && neighbors[6] && neighbors[5] ||
                neighbors[6] && neighbors[5] && neighbors[3] ||
                neighbors[5] && neighbors[3] && neighbors[0]||
                neighbors[3] && neighbors[0] && neighbors[1]);
    }
}
