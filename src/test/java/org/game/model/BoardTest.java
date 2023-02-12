package org.game.model;

import org.game.model.visitor.MoveVisitor;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    private static class CountingVisitor implements MoveVisitor {
        public LinkedList<Move> visited = new LinkedList<>();

        @Override
        public void visit(Move m) {
            visited.add(m);
        }
    }

    private Board subject = Board.initialBoard();

    @Test(expected = AssertionError.class)
    public void testBadCounts() {
        // only fails if assertions enabled
        Board bad = new Board(new int[14], Player.ONE);
        assert false : "Should fail assertion check earlier";
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadCountSize() {
        Board bad = new Board(new int[12], Player.ONE);
    }

    @Test
    public void testInitialScore() {
        assertEquals(36, subject.getScore(Player.ONE));
        assertEquals(0, subject.getSafeScore(Player.ONE));
        assertEquals(36, subject.getScore(Player.TWO));
        assertEquals(0, subject.getSafeScore(Player.TWO));
        assertNull(subject.getLeader());
        List<Move> moves = subject.nextMoves();
        assertNotNull(moves);
        assertEquals(6, moves.size());
    }

    @Test
    public void testMakeMove() {
        // simple move that wraps into opponents board
        Move move = subject.nextMoves().get(5);// last cup
        assertEquals(Player.ONE, move.getPlayer());
        assertEquals(5, move.getCup().getCupNumber());
        Board after = move.getAfter();

        assertEquals(31, after.getScore(Player.ONE));
        assertEquals(1, after.getSafeScore(Player.ONE));
        assertEquals(41, after.getScore(Player.TWO));
        assertEquals(0, after.getSafeScore(Player.TWO));
        assertEquals(Player.TWO, after.getLeader());
        List<Move> moves = after.nextMoves();
        assertNotNull(moves);
        assertEquals(6, moves.size());
        assertEquals(Player.TWO, after.getNextPlayer());

        // a move that gives another go
        move = subject.nextMoves().get(2);
        assertEquals(Player.ONE, move.getPlayer());
        assertEquals(2, move.getCup().getCupNumber());
        after = move.getAfter();

        assertEquals(34, after.getScore(Player.ONE));
        assertEquals(1, after.getSafeScore(Player.ONE));
        assertEquals(38, after.getScore(Player.TWO));
        assertEquals(0, after.getSafeScore(Player.TWO));
        assertEquals(Player.TWO, after.getLeader());
        assertEquals(Player.TWO, after.getNextPlayer());
        moves = after.nextMoves();
        assertNotNull(moves);
        assertEquals(6, moves.size());
    }

    @Test
    public void testSkipOpponentsCupAndSteal() {
        Board before = new Board(new int[]{0,6,6,6,6,12,0,6,6,6,6,6,6,0}, Player.ONE);
        Move move = before.nextMoves().get(4);
        assertEquals(Player.ONE, move.getPlayer());
        assertEquals(5, move.getCup().getCupNumber());
        assertEquals(12, before.getBeadCount(move.getCup()));
        Board after = move.getAfter();

        assertEquals(30, after.getScore(Player.ONE));
        assertEquals(1, after.getSafeScore(Player.ONE));
        assertEquals(42, after.getScore(Player.TWO));
        assertEquals(0, after.getSafeScore(Player.TWO));
        assertEquals(Player.TWO, after.getLeader());
        List<Move> moves = after.nextMoves();
        assertNotNull(moves);
        assertEquals(6, moves.size());
        assertEquals(Player.TWO, after.getNextPlayer());
    }

    @Test
    public void testVisitor() {
        CountingVisitor visitor = new CountingVisitor();
        subject.visit(visitor, 1);
        assertEquals(6, visitor.visited.size());

        visitor = new CountingVisitor();
        subject.visit(visitor, 2);
        assertEquals(41, visitor.visited.size());
    }
}
