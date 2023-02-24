package test;

import upo.graph20035170.QuickUnion;
import upo.graph20035170.QuickUnionByRank;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuickUnionTest {
    private QuickUnion quickUnion;
    private QuickUnionByRank quickUnionByRank;

    @BeforeEach
    public void setUp() {
        quickUnionByRank = new QuickUnionByRank();
        quickUnion = new QuickUnion();
    }

    @Test
    public void testMakeSet() {
        quickUnion.makeSet("A");
        assertEquals("A", quickUnion.find("A"));
    }

    @Test
    public void testMakeSetByRank() {
        quickUnionByRank.makeSet("A");
        assertEquals("A", quickUnionByRank.find("A"));
        assertEquals(0, quickUnionByRank.getRank("A"));
    }

    @Test
    public void testQuickUnion() {
        quickUnion.makeSet("A");
        quickUnion.makeSet("B");
        quickUnion.makeSet("C");
        quickUnion.makeSet("D");
        quickUnion.makeSet("E");

        quickUnion.union("A", "B");
        assertEquals("A", quickUnion.find("B"));

        quickUnion.union("A", "C");
        assertEquals("A", quickUnion.find("C"));

        quickUnion.union("D", "E");
        assertEquals("D", quickUnion.find("E"));

        quickUnion.union("B", "E");
        assertEquals("A", quickUnion.find("E"));
    }

    @Test
    public void testQuickUnionByRank() {
        quickUnionByRank.makeSet("A");
        quickUnionByRank.makeSet("B");
        quickUnionByRank.makeSet("C");
        quickUnionByRank.makeSet("D");
        quickUnionByRank.makeSet("E");

        quickUnionByRank.union("A", "B");
        assertEquals("A", quickUnionByRank.find("B"));
        assertEquals(1, quickUnionByRank.getRank("A"));

        quickUnionByRank.union("A", "C");
        assertEquals("A", quickUnionByRank.find("C"));
        assertEquals(1, quickUnionByRank.getRank("A"));

        quickUnionByRank.union("D", "E");
        assertEquals("D", quickUnionByRank.find("E"));
        assertEquals(1, quickUnionByRank.getRank("D"));

        quickUnionByRank.union("B", "E");
        assertEquals("A", quickUnionByRank.find("E"));
        assertEquals(2, quickUnionByRank.getRank("A"));
    }
}
