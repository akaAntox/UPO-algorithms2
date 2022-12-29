package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import upo.graph20035170.AdjMatrixDirWeight;

public class AdjMatrixDirWeightTest {
    AdjMatrixDirWeight matrixDir = new AdjMatrixDirWeight();

    private void populateGraph(int num) {
        for (char a = 'A'; a < 'A' + num; a++)
            matrixDir.addVertex(new String(new char[] { a }));
    }

    @Test
    public void testFloydWarshall() {
        populateGraph(8);
        matrixDir.addEdge("A", "B");
        matrixDir.setEdgeWeight("A", "B", 2);
        matrixDir.addEdge("A", "D");
        matrixDir.setEdgeWeight("A", "D", 9);
        matrixDir.addEdge("D", "H");
        matrixDir.setEdgeWeight("D", "H", 1);
        matrixDir.addEdge("B", "C");
        matrixDir.setEdgeWeight("B", "C", 5);
        matrixDir.addEdge("B", "F");
        matrixDir.setEdgeWeight("B", "F", 8);
        matrixDir.addEdge("F", "G");
        matrixDir.setEdgeWeight("F", "G", 6);
        matrixDir.addEdge("G", "H");
        matrixDir.setEdgeWeight("G", "H", 1);
        matrixDir.addEdge("G", "F");
        matrixDir.setEdgeWeight("G", "F", 7);
        matrixDir.addEdge("G", "C");
        matrixDir.setEdgeWeight("G", "C", 9);
        var graph = matrixDir.getFloydWarshallShortestPaths();
        assertTrue(graph.containsEdge("A", "B"));
        assertEquals(2.0, graph.getEdgeWeight("A", "B"));
        assertTrue(graph.containsEdge("A", "D"));
        assertEquals(9.0, graph.getEdgeWeight("A", "D"));
        assertTrue(graph.containsEdge("D", "H"));
        assertEquals(1.0, graph.getEdgeWeight("D", "H"));
        assertTrue(graph.containsEdge("B", "C"));
        assertEquals(5.0, graph.getEdgeWeight("B", "C"));
        assertTrue(graph.containsEdge("B", "F"));
        assertEquals(8.0, graph.getEdgeWeight("B", "F"));
        assertTrue(graph.containsEdge("F", "G"));
        assertEquals(6.0, graph.getEdgeWeight("F", "G"));
        assertTrue(graph.containsEdge("G", "H"));
        assertEquals(1.0, graph.getEdgeWeight("G", "H"));
        assertTrue(graph.containsEdge("G", "F"));
        assertEquals(7.0, graph.getEdgeWeight("G", "F"));
        assertTrue(graph.containsEdge("G", "C"));
        assertEquals(9.0, graph.getEdgeWeight("G", "C"));
    }
}
