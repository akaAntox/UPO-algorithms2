package test;

import upo.graph20035170.UnionFind;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnionFindTest {
    private UnionFind unionFind;

    @BeforeEach
    public void setUp() {
        unionFind = new UnionFind();
        unionFind.makeSet("A");
        unionFind.makeSet("B");
        unionFind.makeSet("C");
        unionFind.makeSet("D");
        unionFind.makeSet("E");
    }

    @Test
    public void testUnionFind() {
        assertEquals("A", unionFind.find("A"));
        unionFind.union("A", "B");
        assertEquals("A", unionFind.find("B"));
        unionFind.union("A", "C");
        assertEquals("A", unionFind.find("C"));
        unionFind.union("D", "E");
        assertEquals("D", unionFind.find("E"));
        unionFind.union("B", "E");
        assertEquals("A", unionFind.find("E"));
    }
}
