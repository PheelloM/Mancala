package org.game.player;

import org.game.model.Board;
import org.game.model.Move;

public class RemotePlayer implements MoveSupplier{

    @Override
    public Move selectFrom(Board board) {
        // FIXME listen on a socket for a remote player commands
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Remote player";
    }
}
