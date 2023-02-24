package upo.graph20035170;

public class QuickFind extends UnionFind {
    public QuickFind() {
        super();
    }

    @Override
    public String find(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        return forest.getPartent(vertex);
    }

    protected void setUnion(String vertex1, String vertex2) {
        for (int i = 0; i < graph.size(); i++) {
            if (forest.getPartent(graph.getVertexLabel(i)).equals(vertex2)) {
                graph.addEdge(vertex1, graph.getVertexLabel(i));
                forest.setParent(graph.getVertexLabel(i), vertex1);
            }
        }
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
        setUnion(parent1, parent2);
    }
}