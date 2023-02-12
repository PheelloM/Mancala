package org.game.model.visitor;

import org.game.model.Move;

public interface MoveVisitor {
    /**
     * A visitor to all the moves in a board
     * @param move
     */

    void visit(Move move);
}
