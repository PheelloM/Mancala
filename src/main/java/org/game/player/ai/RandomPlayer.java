package org.game.player.ai;

import org.game.model.Board;
import org.game.model.Move;
import org.game.player.MoveSupplier;

import java.util.List;

public class RandomPlayer implements MoveSupplier {
    @Override
    public Move selectFrom(Board board) {
        List<Move> moves = board.nextMoves();
        int selection = (int) (Math.random() * moves.size());
        return (Move) moves.get(selection);
    }

    @Override
    public String getDisplayName() {
        return "Random player";
    }
}
