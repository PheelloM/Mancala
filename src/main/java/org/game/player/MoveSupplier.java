package org.game.player;

import org.game.model.Board;
import org.game.model.Move;

/**
 * Interface to a class that can use it's own internal strategy to decide between a series of moves
 */
public interface MoveSupplier {
    /**
     * Given a board - choose a move
     * @param board the board state to choose a move from
     * @return the selected move
     */
    Move selectFrom(Board board);

    /**
     * A display name for this move supplier
     */
    String getDisplayName();
}
