package upo.graph20035170;

public class QuickFindBalanced extends QuickFind {
    private Integer[] size;

    public QuickFindBalanced() {
        super();
        size = new Integer[0];
    }

    public int getSize(String vertex) {
        return size[graph.getVertexIndex(vertex)];
    }

    @Override
    public void makeSet(String vertex) {
        super.makeSet(vertex);
        Integer k = graph.size();
        var tempSize = new Integer[k];
        System.arraycopy(size, 0, tempSize, 0, size.length);
        tempSize[k - 1] = 1;
        size = tempSize;
    }

    @Override
    protected void setUnion(String vertex1, String vertex2) {
        for (int i = 0; i < graph.size(); i++) {
            if (forest.getPartent(graph.getVertexLabel(i)).equals(vertex2)) {
                graph.addEdge(vertex1, graph.getVertexLabel(i));
                forest.setParent(graph.getVertexLabel(i), vertex1);
                size[graph.getVertexIndex(vertex1)]++;
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
        int size1 = getSize(parent1);
        int size2 = getSize(parent2);
        if (size1 < size2) {
            setUnion(parent2, parent1);
        } else {
            setUnion(parent1, parent2);
        }
    }
}