package org.game.player;

import org.game.model.Board;
import org.game.model.Move;
import org.game.player.human.GameInput;

import java.util.Iterator;
import java.util.List;

/**
 * A move supplier where a human chooses the next move
 */
public class HumanPlayer implements MoveSupplier{

    private GameInput input;

    public HumanPlayer(GameInput input) {
        this.input = input;
    }

    @Override
    public Move selectFrom(Board board) {
        List<Move> moves = board.nextMoves();
        System.out.println("Moves:");
        Iterator iter = moves.iterator();
        while (iter.hasNext()) {
            Move m = (Move) iter.next();
            System.out.println(m);
        }
        int chosen = -1;
        while (chosen < 0 || chosen > moves.size()) {
            System.out.println("Please choose a move (or undo): ");
            String command = input.getCommand();
            if (command.startsWith("undo")) {
                return null;
            }
            try {
                chosen = Integer.parseInt(command);
            } catch (Exception e) {
                System.out.println("Error: Must be a number!");
            }
        }
        return moves.get(chosen);
    }

    @Override
    public String getDisplayName() {
        return "Human console interactive player";
    }

}
