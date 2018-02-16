package GameOfLife;

//New Cells are born if the neighbor count is 3
//Cells survive if the neighbor count is 2 or 3
public class ConwaysCellRules implements CellLivingRule {
    @Override
    public boolean aliveNextRound(Board board, int cellx, int celly) {
        int _count = board.countNeighbors(cellx, celly);
        return _count == 3 || (_count == 2 && board.getCell(cellx, celly));
    }
}
