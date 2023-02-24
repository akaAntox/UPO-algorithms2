package upo.graph20035170;

public class QuickUnionByRank extends QuickUnion {
    private Integer[] rank;

    public QuickUnionByRank() {
        super();
        rank = new Integer[0];
    }

    public int getRank(String vertex) {
        return rank[graph.getVertexIndex(vertex)];
    }

    @Override
    public void makeSet(String vertex) {
        super.makeSet(vertex);
        Integer k = graph.size();
        var tempRank = new Integer[k];
        System.arraycopy(rank, 0, tempRank, 0, rank.length);
        tempRank[k - 1] = 0;
        rank = tempRank;
    }

    @Override
    public String find(String vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null");
        }
        if (!forest.getPartent(vertex).equals(vertex)) {
            // Path compression
            forest.setParent(vertex, find(forest.getPartent(vertex)));
            // Path halving
            rank[graph.getVertexIndex(vertex)] = rank[graph.getVertexIndex(forest.getPartent(vertex))];
        }
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
        int rank1 = getRank(parent1);
        int rank2 = getRank(parent2);
        if (rank1 < rank2) {
            graph.addEdge(parent2, parent1);
            forest.setParent(parent1, parent2);
        } else {
            graph.addEdge(parent1, parent2);
            forest.setParent(parent2, parent1);
            if (rank1 == rank2) {
                rank[graph.getVertexIndex(parent1)] = rank1 + 1;
            }
        }
    }
}