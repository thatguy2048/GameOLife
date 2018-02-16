package GameOfLife;

//New cells are born if the neighbor count is 3 or 6
//Cells survive if the neighbor count is 2 or 3
public class HighlifeCellRules implements CellLivingRule {
    @Override
    public boolean aliveNextRound(Board board, int cellx, int celly) {
        int _count = board.countNeighbors(cellx, celly);
        return _count == 3 || ( board.getCell(cellx, celly)?_count==2:_count==6 );
    }
}
