package org.game.player.ai;

import org.game.model.Board;
import org.game.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreProbabilityAccumulatorTest {
    private ScoreProbabilityAccumulator subject = new ScoreProbabilityAccumulator(Player.ONE);

    @Test
    public void visit() {
        Board.initialBoard().visit(subject, 3);
        assertEquals(231, subject.getTotalPopulationSize());
        assertEquals(-4.675324675324675, subject.getAverageLead(), 0.00001);
    }

}
