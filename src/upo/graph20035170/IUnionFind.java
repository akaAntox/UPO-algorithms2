package upo.graph20035170;

public interface IUnionFind {

    public void makeSet(String vertex);

    public String find(String vertex);

    public void union(String vertex1, String vertex2);

}
