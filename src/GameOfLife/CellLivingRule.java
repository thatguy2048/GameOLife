package GameOfLife;

public interface CellLivingRule {
    boolean aliveNextRound(Board board, int cellx, int celly);
}
