package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

import upo.graph20035170.AdjMatrixDir;
import upo.graph20035170.AdjMatrixDirWeight;

public class AdjMatrixDirTest {
    AdjMatrixDir matrixDir = new AdjMatrixDir();

    private void populateGraph(int num) {
        for (char a = 'A'; a < 'A' + num; a++)
            matrixDir.addVertex(new String(new char[] { a }));
    }

    @Test
    public void testAddVertex() {
        assertEquals(0, matrixDir.addVertex("A"));
        assertEquals(1, matrixDir.addVertex("B"));
        assertEquals(2, matrixDir.addVertex("C"));
    }

    @Test
    public void testContainsVertex() {
        assertEquals(0, matrixDir.addVertex("A"));
        assertEquals(1, matrixDir.addVertex("B"));
        assertEquals(2, matrixDir.addVertex("C"));
        assertTrue(matrixDir.containsVertex("A"));
        assertTrue(matrixDir.containsVertex("B"));
        assertTrue(matrixDir.containsVertex("C"));
        assertEquals(3, matrixDir.size());
    }

    @Test
    public void testEdgeMethods() {
        assertEquals(0, matrixDir.addVertex("A"));
        assertEquals(1, matrixDir.addVertex("B"));
        assertEquals(2, matrixDir.addVertex("C"));
        assertTrue(matrixDir.containsVertex("A"));
        assertTrue(matrixDir.containsVertex("B"));
        assertTrue(matrixDir.containsVertex("C"));
        assertEquals(3, matrixDir.size());

        matrixDir.addEdge("A", "B");
        assertTrue(matrixDir.containsEdge("A", "B"));
        assertFalse(matrixDir.containsEdge("B", "A"));
        assertThrows(IllegalArgumentException.class, () -> matrixDir.containsEdge("C", "D"));
    }

    @Test
    public void testTrueEquals() {
        AdjMatrixDir tempEqualsMatrix = new AdjMatrixDir();
        assertEquals(0, matrixDir.addVertex("A"));
        assertEquals(0, tempEqualsMatrix.addVertex("A"));
        assertEquals(1, matrixDir.addVertex("B"));
        assertEquals(1, tempEqualsMatrix.addVertex("B"));
        assertEquals(2, matrixDir.addVertex("C"));
        assertEquals(2, tempEqualsMatrix.addVertex("C"));
        assertEquals(3, matrixDir.addVertex("D"));
        assertEquals(3, tempEqualsMatrix.addVertex("D"));

        matrixDir.addEdge("A", "B");
        tempEqualsMatrix.addEdge("A", "B");
        matrixDir.addEdge("C", "D");
        tempEqualsMatrix.addEdge("C", "D");

        assertTrue(matrixDir.equals(tempEqualsMatrix));
        assertThrows(NoSuchElementException.class, () -> matrixDir.getEdgeWeight("A", "C"));

    }

    @Test
    public void testFalseEquals() {
        AdjMatrixDir tempEqualsMatrix = new AdjMatrixDir();
        assertEquals(0, matrixDir.addVertex("A"));
        assertEquals(0, tempEqualsMatrix.addVertex("A"));
        assertEquals(1, matrixDir.addVertex("B"));
        assertEquals(1, tempEqualsMatrix.addVertex("B"));
        assertEquals(2, matrixDir.addVertex("C"));
        assertEquals(2, tempEqualsMatrix.addVertex("C"));
        assertEquals(3, matrixDir.addVertex("D"));
        assertEquals(3, tempEqualsMatrix.addVertex("D"));

        matrixDir.addEdge("A", "B");
        tempEqualsMatrix.addEdge("A", "B");
        matrixDir.addEdge("A", "D");
        tempEqualsMatrix.addEdge("C", "B");

        assertFalse(matrixDir.equals(tempEqualsMatrix));

    }

    @Test
    public void testFalseEqualsDifferentClass() {
        AdjMatrixDirWeight anotherMatrix = new AdjMatrixDirWeight();
        assertFalse(matrixDir.equals(anotherMatrix));
    }

    @Test
    public void testExceptionCallingEdgeWeight() {
        assertThrows(UnsupportedOperationException.class, () -> matrixDir.setEdgeWeight("A", "B", 1.0));
        assertThrows(IllegalArgumentException.class, () -> matrixDir.getEdgeWeight("A", "B"));
    }

    @Test
    public void testDFSTree() {
        int[] timings = { 6, 3, 5, 8, 12, 11 };
        populateGraph(6);
        matrixDir.addEdge("A", "B");
        matrixDir.addEdge("A", "C");
        matrixDir.addEdge("D", "B");
        matrixDir.addEdge("E", "A");
        matrixDir.addEdge("E", "F");
        var forest = matrixDir.getDFSTOTForest("A");
        for (int i = 0; i < matrixDir.size(); i++)
            assertEquals(timings[i], forest.getEndTime(matrixDir.getVertexLabel(i)));

    }

    @Test
    public void testBFSTree() {
        populateGraph(6);
        matrixDir.addEdge("A", "B");
        matrixDir.addEdge("A", "C");
        matrixDir.addEdge("D", "B");
        matrixDir.addEdge("E", "A");
        matrixDir.addEdge("E", "F");
        matrixDir.addEdge("A", "E");
        matrixDir.addEdge("B", "D");
        var visit = matrixDir.getBFSTree("A");
        assertNotNull(visit);
        String[] vertices = new String[matrixDir.size()];
        for (int i = matrixDir.getVertexIndex("A") + 1; i < matrixDir.size(); i++) {
            char a = (char) ('A' + i);
            vertices[i] = visit.getPartent(new String(new char[] { a }));
        }
        var arrayResult = new String[] { null, "A", "A", "B", "A", "E" };
        assertArrayEquals(arrayResult, vertices);
    }

    @Test
    public void testStronglyConnectedComponents() {
        populateGraph(10);
        matrixDir.addEdge("A", "F");
        matrixDir.addEdge("A", "E");
        matrixDir.addEdge("B", "A");
        matrixDir.addEdge("C", "B");
        matrixDir.addEdge("C", "D");
        matrixDir.addEdge("C", "G");
        matrixDir.addEdge("D", "C");
        matrixDir.addEdge("E", "A");
        matrixDir.addEdge("E", "H");
        matrixDir.addEdge("F", "B");
        matrixDir.addEdge("F", "E");
        matrixDir.addEdge("F", "H");
        matrixDir.addEdge("G", "C");
        matrixDir.addEdge("G", "F");
        matrixDir.addEdge("G", "I");
        matrixDir.addEdge("I", "H");
        matrixDir.addEdge("I", "J");
        matrixDir.addEdge("J", "I");

        var components = matrixDir.stronglyConnectedComponents();
        assertEquals(4, components.size());
    }

    @Test
    public void testEdgeWeight() {
        populateGraph(6);
        matrixDir.addEdge("A", "B");
        matrixDir.addEdge("A", "C");
        matrixDir.addEdge("D", "B");
        matrixDir.addEdge("E", "A");
        matrixDir.addEdge("E", "F");
        assertEquals(1.0, matrixDir.getEdgeWeight("A", "B"));
        assertThrows(NoSuchElementException.class, () -> matrixDir.getEdgeWeight("A", "D"));
    }

    @Test
    public void testRemoves() {
        populateGraph(10);
        matrixDir.addEdge("A", "F");
        matrixDir.addEdge("A", "E");
        matrixDir.addEdge("B", "A");
        matrixDir.addEdge("C", "B");
        matrixDir.addEdge("C", "D");
        matrixDir.addEdge("C", "G");
        matrixDir.addEdge("D", "C");
        matrixDir.addEdge("E", "A");
        matrixDir.addEdge("E", "H");
        matrixDir.addEdge("F", "B");
        matrixDir.addEdge("F", "E");
        matrixDir.addEdge("F", "H");
        matrixDir.addEdge("G", "C");
        matrixDir.addEdge("G", "F");
        matrixDir.addEdge("G", "I");
        matrixDir.addEdge("I", "H");
        matrixDir.addEdge("I", "J");
        matrixDir.addEdge("J", "I");
        matrixDir.removeVertex("H");
        assertThrows(IllegalArgumentException.class, () -> matrixDir.containsEdge("F", "H"));
        assertFalse(matrixDir.containsVertex("H"));
        var cc = matrixDir.stronglyConnectedComponents();
        assertEquals(3, cc.size());
    }
}