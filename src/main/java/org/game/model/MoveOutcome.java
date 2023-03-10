package org.game.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the temporary board state for a move outcome
 */
@Data
@Builder
public class MoveOutcome {
    private final int[] beadCount;
    private final boolean anotherGo;
}
