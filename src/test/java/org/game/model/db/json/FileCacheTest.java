package org.game.model.db.json;

import org.game.model.Board;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FileCacheTest {

    private static final String EXPECTED_COUNTS = "6,6,6,6,6,6,0,6,6,6,6,6,6,0";
    private static final String EXPECTED_MOVES = "A0_A1_A2_A3_A4_A5";

    private static final String EXPECTED_BOARD = "A\n36\n36\n" + EXPECTED_COUNTS +"\n" + EXPECTED_MOVES + "\n";

    private Board testBoard = Board.initialBoard();

    @Test
    public void testCupCounts() {
        assertEquals(EXPECTED_COUNTS, FileCache.cupCounts(testBoard));
        assertArrayEquals(testBoard.cloneBeads(), FileCache.parseCupCounts(EXPECTED_COUNTS));
    }

    @Test
    public void testMoveFormat() {
        assertEquals(EXPECTED_MOVES, FileCache.format(testBoard.nextMoves()));
        assertEquals(testBoard.nextMoves().stream().map(move -> move.getCup()).collect(Collectors.toList()), FileCache.parseNextMoves(EXPECTED_MOVES));
    }

    @Test
    public void testWrite() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream testStream = new PrintStream(out, true, StandardCharsets.UTF_8.name());
        FileCache.writeTo(testStream, testBoard);
        assertEquals(EXPECTED_BOARD, new String(out.toByteArray(), StandardCharsets.UTF_8).replace("\r", ""));
    }

    @Test
    public void testRead() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(EXPECTED_BOARD));
        Board board = FileCache.readFrom(reader);
        assertNotNull(board);
        assertEquals(testBoard, board);
    }
}
