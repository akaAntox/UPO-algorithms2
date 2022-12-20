package upo.graph20035170;

public class AdjMatrixDir extends AdjMatrixDirWeight {

    public AdjMatrixDir() {
        super();
    }

    @Override
    public void setEdgeWeight(String vertex1, String vertex2, double weight) {
        throw new UnsupportedOperationException("Unweighted graph.");
    }
}
