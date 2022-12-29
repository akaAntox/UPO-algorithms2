package upo.graph20035170;

import upo.graph.base.WeightedGraph;

public class AdjMatrixDir extends AdjMatrixDirWeight {

    public AdjMatrixDir() {
        super();
    }

    @Override
    public void setEdgeWeight(String vertex1, String vertex2, double weight) {
        throw new UnsupportedOperationException("Cannot set weight: unweighted graph.");
    }

    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot use Floyd-Warshall: unweighted graph.");
    }
}
