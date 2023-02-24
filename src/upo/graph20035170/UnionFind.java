package upo.graph20035170;

import upo.graph.base.Graph;
import upo.graph.base.VisitForest;

// Generic implementation of union find
public class UnionFind implements IUnionFind {
    protected Graph graph = new AdjMatrixDir();
    protected VisitForest forest;

    @Override
    public void makeSet(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        graph.addVertex(vertex);
        var newForest = new VisitForest(graph, null);
        newForest.setParent(vertex, vertex);

        if (forest != null)
            for (int i = 0; i < graph.size() - 1; i++)
                newForest.setParent(graph.getVertexLabel(i), forest.getPartent(graph.getVertexLabel(i)));

        forest = newForest;
    }

    @Override
    public String find(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        if (!forest.getPartent(vertex).equals(vertex))
            forest.setParent(vertex, find(forest.getPartent(vertex)));
        return forest.getPartent(vertex);
    }

    @Override
    public void union(String vertex1, String vertex2) {
        if (vertex1 == null || vertex2 == null) {
            throw new IllegalArgumentException("Vertexes cannot be null");
        }
        String parent1 = find(vertex1);
        String parent2 = find(vertex2);
        if (parent1 == null || parent2 == null || parent1.equals(parent2)) {
            return;
        }
        graph.addEdge(parent1, parent2);
        forest.setParent(parent2, parent1);
    }
}