package org.game.player.ai;

import org.game.model.Board;
import org.game.model.Move;
import org.game.player.MoveSupplier;
import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * A computer player can search through a vast array of possible next moves to
 * evaluate which one maximises the pay-off to the player.
 */
public class ComputerPlayer implements MoveSupplier {
    /**
     * Logger (SLF4J)
     */
    private static final Logger LOG = getLogger(ComputerPlayer.class);

    private int depth = 5;

    @Override
    public Move selectFrom(Board board) {
        return doBestScoreMove(board);
    }

    /**
     * Selects the move to give this player the best score possible after the move.
     *
     * @param board The board to pick the best score from
     * @return The last move with the highest score
     */
    public Move doBestScoreMove(Board board) {
        List<Move> moves = board.nextMoves();
        double bestScore = Double.NEGATIVE_INFINITY;
        Move bestMove = null;
        for(Move m : moves) {
            ScoreProbabilityAccumulator accumulator = new ScoreProbabilityAccumulator(board.getNextPlayer());
            m.getAfter().visit(accumulator, depth);
            double score = accumulator.getAverageLead();
            if (LOG.isDebugEnabled()){
                LOG.debug(m.getCup().toString() + ":"+score);
            }

            if (bestMove == null || score > bestScore) {
                bestMove = m;
                bestScore = score;
            }
        }
        return bestMove;
    }

    @Override
    public String getDisplayName() {
        return "Computer AI Player";
    }
}
