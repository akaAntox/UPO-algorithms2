package upo.graph20035170;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import upo.graph.base.VisitForest;
import upo.graph.base.WeightedGraph;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

public class AdjMatrixDirWeight implements WeightedGraph {

    private static final String OPERATION_NOT_SUPPORTED = "Operation not supported";
    private static final String INCORRECT_VERTEX = "Incorrect vertex";
    private static final String INCORRECT_SOURCE_ARGUMENT = "Incorrect source argument";
    private static final String INCORRECT_DESTINATION_ARGUMENT = "Incorrect destination argument";
    protected double[][] matrix;
    protected ArrayList<String> vertices;
    private int time = 0;

    public AdjMatrixDirWeight() {
        matrix = new double[size()][size()];
        vertices = new ArrayList<>();
    }

    @Override
    public void addEdge(String src, String dest) throws IllegalArgumentException {
        if (!containsVertex(src))
            throw new IllegalArgumentException(INCORRECT_SOURCE_ARGUMENT);
        if (!containsVertex(dest))
            throw new IllegalArgumentException(INCORRECT_DESTINATION_ARGUMENT);

        matrix[getVertexIndex(src)][getVertexIndex(dest)] = defaultEdgeWeight;
    }

    @Override
    public int addVertex(String vertex) {
        vertices.add(vertex);
        var newMatrix = new double[size()][size()];
        System.arraycopy(matrix, 0, newMatrix, 0, size());
        return size() - 1;
    }

    @Override
    public Set<Set<String>> connectedComponents() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED);
    }

    @Override
    public boolean containsEdge(String src, String dest) throws IllegalArgumentException {
        if (!containsVertex(src))
            throw new IllegalArgumentException(INCORRECT_SOURCE_ARGUMENT);
        if (!containsVertex(dest))
            throw new IllegalArgumentException(INCORRECT_DESTINATION_ARGUMENT);

        return matrix[getVertexIndex(src)][getVertexIndex(dest)] > 0;
    }

    @Override
    public boolean containsVertex(String vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public Set<String> getAdjacent(String vertex) throws NoSuchElementException {
        if (!containsVertex(vertex))
            throw new NoSuchElementException(INCORRECT_VERTEX);

        var adjacentVertices = new TreeSet<String>();

        for (int i = 0; i < size(); i++)
            if (matrix[getVertexIndex(vertex)][i] > 0)
                adjacentVertices.add(getVertexLabel(i));

        return adjacentVertices;
    }

    @Override
    public VisitForest getBFSTree(String vertex) throws UnsupportedOperationException, IllegalArgumentException {
        if (!containsVertex(vertex))
            throw new IllegalArgumentException(INCORRECT_VERTEX);

        var forest = new VisitForest(this, VisitType.BFS);
        Queue<String> verticesQueue = new LinkedList<>();

        forest.setColor(vertex, Color.GRAY);
        verticesQueue.add(vertex);

        while (!verticesQueue.isEmpty()) {
            for (var adj : getAdjacent(vertex)) {
                if (forest.getColor(adj) == Color.WHITE) {
                    forest.setColor(adj, Color.GRAY);
                    forest.setParent(adj, vertex);
                    verticesQueue.add(adj);
                }
            }
            forest.setColor(vertex, Color.BLACK);
            verticesQueue.remove(vertex);
        }

        return forest;
    }

    @Override
    public VisitForest getDFSTOTForest(String vertex) throws UnsupportedOperationException, IllegalArgumentException {
        if (!containsVertex(vertex))
            throw new IllegalArgumentException(INCORRECT_VERTEX);

        time = 1;
        var forest = new VisitForest(this, VisitType.DFS);
        recDFSVisit(forest, vertex);

        for (var element : vertices)
            if (forest.getColor(element) == Color.WHITE)
                recDFSVisit(forest, element);

        return forest;
    }

    @Override
    public VisitForest getDFSTOTForest(String[] vertex) throws UnsupportedOperationException, IllegalArgumentException {
        if (vertex.length != vertices.size())
            throw new IllegalArgumentException("Incorrect vertices");

        time = 1;
        var forest = new VisitForest(this, VisitType.DFS);

        for (var element : vertices)
            if (forest.getColor(element) == Color.WHITE)
                recDFSVisit(forest, element);

        return forest;
    }

    @Override
    public VisitForest getDFSTree(String vertex) throws UnsupportedOperationException, IllegalArgumentException {
        if (!containsVertex(vertex))
            throw new IllegalArgumentException(INCORRECT_VERTEX);

        time = 1;
        var forest = new VisitForest(this, VisitType.DFS);
        recDFSVisit(forest, vertex);

        return forest;
    }

    public void recDFSVisit(VisitForest forest, String vertex) {
        forest.setColor(vertex, Color.GRAY);
        forest.setStartTime(vertex, time);
        time++;

        for (var element : getAdjacent(vertex)) {
            if (forest.getColor(element) == Color.WHITE) {
                forest.setParent(element, vertex);
            }
        }

        forest.setColor(vertex, Color.BLACK);
        forest.setEndTime(vertex, time);
        time++;
    }

    @Override
    public int getVertexIndex(String vertex) {
        return vertices.indexOf(vertex);
    }

    @Override
    public String getVertexLabel(Integer vertex) {
        return vertices.get(vertex);
    }

    @Override
    public boolean isAdjacent(String src, String dest) throws IllegalArgumentException {
        return containsEdge(src, dest);
    }

    @Override
    public boolean isCyclic() {
        var forest = new VisitForest(this, null);

        for (var element : vertices)
            if ((forest.getColor(element) == Color.WHITE) && visitCycle(forest, element))
                return true;

        return false;
    }

    private boolean visitCycle(VisitForest forest, String element) {
        forest.setColor(element, Color.GRAY);

        for (var adj : getAdjacent(element)) {
            if (forest.getColor(adj) == Color.WHITE) {
                forest.setParent(adj, element);
                if (visitCycle(forest, adj))
                    return true;
            } else if (adj.equals(forest.getPartent(element)))
                return true;
        }

        forest.setColor(element, Color.BLACK);
        return false;
    }

    @Override
    public boolean isDAG() {
        return isDirected() && !isCyclic();
    }

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public void removeEdge(String src, String dest) throws IllegalArgumentException, NoSuchElementException {
        if (!containsVertex(src))
            throw new IllegalArgumentException(INCORRECT_SOURCE_ARGUMENT);
        if (!containsVertex(dest))
            throw new IllegalArgumentException(INCORRECT_DESTINATION_ARGUMENT);

        matrix[getVertexIndex(src)][getVertexIndex(dest)] = 0;
    }

    @Override
    public void removeVertex(String vertex) throws NoSuchElementException {
        vertices.remove(vertex);
        var newMatrix = new double[size()][size()];
        System.arraycopy(matrix, 0, newMatrix, 0, size());
    }

    @Override
    public int size() {
        return vertices.size();
    }

    @Override
    public Set<Set<String>> stronglyConnectedComponents() throws UnsupportedOperationException {
        Set<Set<String>> components = new HashSet<>();
        var forest = getDFSTOTForest(vertices.get(0));
        var transposedGraph = getTransposedGraph(forest);

        forest = new VisitForest(this, VisitType.DFS);
        for (var element : transposedGraph.vertices) {
            if (forest.getColor(element) == Color.WHITE) {
                components.add(getDFSTree(forest, element));
            }
        }

        return components;
    }

    private Set<String> getDFSTree(VisitForest forest, String vertex) {
        Set<String> setCFC = new HashSet<>();
        recDFSVisit(forest, vertex, setCFC);

        return setCFC;
    }

    private void recDFSVisit(VisitForest forest, String vertex, Set<String> setCFC) {
        forest.setColor(vertex, Color.GRAY);
        forest.setStartTime(vertex, time);
        time++;

        setCFC.add(vertex);
        for (var element : getAdjacent(vertex)) {
            if (forest.getColor(element) == Color.WHITE) {
                forest.setParent(element, vertex);
                recDFSVisit(forest, element, setCFC);
            }
        }

        forest.setColor(vertex, Color.BLACK);
        forest.setEndTime(vertex, time);
        time++;
    }

    private AdjMatrixDirWeight getTransposedGraph(VisitForest forest) {
        Integer[] array = new Integer[size()];
        Integer[] sortedArray = new Integer[size()];

        for (var element : vertices) {
            array[getVertexIndex(element)] = forest.getEndTime(element);
            sortedArray[getVertexIndex(element)] = forest.getEndTime(element);
        }

        Arrays.sort(sortedArray, Collections.reverseOrder());

        var transposedGraph = new AdjMatrixDirWeight();

        for (var element : sortedArray)
            transposedGraph.vertices.add(getVertexLabel(Arrays.asList(array).indexOf(element)));

        for (var i = 0; i < size(); i++)
            for (var j = 0; j < size(); j++)
                transposedGraph.matrix[i][j] = matrix[j][i];

        return transposedGraph;
    }

    @Override
    public String[] topologicalSort() throws UnsupportedOperationException {
        var forest = new VisitForest(this, VisitType.DFS);
        var order = new ArrayList<String>(size());

        for (var element : vertices)
            if (forest.getColor(element) == Color.WHITE)
                recTopologicalDFS(forest, element, order);

        return (String[]) order.toArray();
    }

    private void recTopologicalDFS(VisitForest forest, String element, ArrayList<String> order) {
        forest.setColor(element, Color.GRAY);
        forest.setStartTime(element, time);
        forest.setDistance(element, time++); // d[u] <- time <- time+1

        for (var adj : getAdjacent(element))
            if (forest.getColor(adj) == Color.WHITE) {
                forest.setParent(adj, element);
                recTopologicalDFS(forest, adj, order);
            }

        forest.setColor(element, Color.BLACK);
        forest.setEndTime(element, time++);
        order.add(0, element);
    }

    @Override
    public WeightedGraph getBellmanFordShortestPaths(String arg0)
            throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED);
    }

    @Override
    public WeightedGraph getDijkstraShortestPaths(String arg0)
            throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED);
    }

    @Override
    public double getEdgeWeight(String src, String dest) throws IllegalArgumentException, NoSuchElementException {
        if (!containsVertex(src))
            throw new IllegalArgumentException(INCORRECT_SOURCE_ARGUMENT);
        if (!containsVertex(dest))
            throw new IllegalArgumentException(INCORRECT_DESTINATION_ARGUMENT);
        if (matrix[getVertexIndex(src)][getVertexIndex(dest)] <= 0)
            throw new NoSuchElementException("Edge doesn't exist");

        return matrix[getVertexIndex(src)][getVertexIndex(dest)];
    }

    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        var graph = new AdjMatrixDirWeight();
        setMatrixEdges(graph);
        setShortestPath(graph);
        return graph;
    }

    private void setMatrixEdges(AdjMatrixDirWeight graph) {
        for (var i = 0; i < size(); i++)
            for (var j = 0; j < size(); j++)
                if (i == j)
                    graph.matrix[i][j] = 0;
                else if (containsEdge(getVertexLabel(i), getVertexLabel(j)))
                    graph.matrix[i][j] = getEdgeWeight(getVertexLabel(i), getVertexLabel(j));
                else
                    graph.matrix[i][j] = Double.POSITIVE_INFINITY;
    }

    private void setShortestPath(AdjMatrixDirWeight graph) {
        for (var k = 0; k < size(); k++)
            for (var i = 0; i < size(); i++)
                for (var j = 0; j < size(); j++)
                    if (graph.matrix[i][j] > graph.matrix[i][k] + graph.matrix[k][j])
                        graph.matrix[i][j] = graph.matrix[i][k] + graph.matrix[k][j];
    }

    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED);
    }

    @Override
    public WeightedGraph getPrimMST(String arg0) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void setEdgeWeight(String src, String dest, double weight)
            throws IllegalArgumentException, NoSuchElementException {
        if (!containsVertex(src))
            throw new IllegalArgumentException(INCORRECT_SOURCE_ARGUMENT);
        if (!containsVertex(dest))
            throw new IllegalArgumentException(INCORRECT_DESTINATION_ARGUMENT);
        if (weight <= 0)
            throw new IllegalArgumentException("Incorrect weight argument");

        if (matrix[getVertexIndex(src)][getVertexIndex(dest)] <= 0)
            throw new NoSuchElementException("Edge doesn't exist");

        matrix[getVertexIndex(src)][getVertexIndex(dest)] = weight;
    }

    @Override
    public boolean equals(Object graph) {
        if (graph == null)
            return false;
        if (graph.getClass() != getClass())
            return false;
        AdjMatrixDirWeight comparedTo = (AdjMatrixDirWeight) graph;

        if (comparedTo.size() != size())
            return false;

        for (int i = 0; i < size(); i++)
            if (comparedTo.getVertexLabel(i) != vertices.get(i))
                return false;
        return compareEdges(comparedTo);
    }

    private boolean compareEdges(WeightedGraph comparedTo) {
        for (int i = 0; i < size(); i++)
            for (int j = 0; j < size(); j++) {
                if (comparedTo.containsEdge(comparedTo.getVertexLabel(i),
                        comparedTo.getVertexLabel(j)) != containsEdge(vertices.get(i), vertices.get(j)))
                    return false;
                if (comparedTo.containsEdge(comparedTo.getVertexLabel(i), comparedTo.getVertexLabel(j))
                        && containsEdge(vertices.get(i), vertices.get(j))
                        && getEdgeWeight(vertices.get(i), vertices.get(j)) != comparedTo
                                .getEdgeWeight(comparedTo.getVertexLabel(i), comparedTo.getVertexLabel(j)))
                    return false;
            }
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + vertices.size() + Arrays.hashCode(matrix);
    }
}
