package upo.graph20035170;

public class QuickUnion extends UnionFind {
    public QuickUnion() {
        super();
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
            throw new IllegalArgumentException("Vertex cannot be null");
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