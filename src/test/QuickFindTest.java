package test;

import upo.graph20035170.QuickFind;
import upo.graph20035170.QuickFindBalanced;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuickFindTest {
    private QuickFindBalanced quickFindBalanced;
    private QuickFind quickFind;

    @BeforeEach
    public void setUp() {
        quickFindBalanced = new QuickFindBalanced();
        quickFind = new QuickFind();
    }

    @Test
    public void testMakeSet() {
        quickFind.makeSet("A");
        assertEquals("A", quickFind.find("A"));
    }

    @Test
    public void testMakeSetBalanced() {
        quickFindBalanced.makeSet("A");
        assertEquals("A", quickFindBalanced.find("A"));
        assertEquals(1, quickFindBalanced.getSize("A"));
    }

    @Test
    public void testQuickUnion() {
        quickFind.makeSet("A");
        quickFind.makeSet("B");
        quickFind.makeSet("C");
        quickFind.makeSet("D");
        quickFind.makeSet("E");

        quickFind.union("A", "B");
        assertEquals("A", quickFind.find("B"));

        quickFind.union("A", "C");
        assertEquals("A", quickFind.find("C"));

        quickFind.union("D", "E");
        assertEquals("D", quickFind.find("E"));

        quickFind.union("B", "E");
        assertEquals("A", quickFind.find("E"));
    }

    @Test
    public void testQuickUnionBalanced() {
        quickFindBalanced.makeSet("A");
        quickFindBalanced.makeSet("B");
        quickFindBalanced.makeSet("C");
        quickFindBalanced.makeSet("D");
        quickFindBalanced.makeSet("E");

        quickFindBalanced.union("A", "B");
        assertEquals("A", quickFindBalanced.find("B"));
        assertEquals(2, quickFindBalanced.getSize("A"));

        quickFindBalanced.union("A", "C");
        assertEquals("A", quickFindBalanced.find("C"));
        assertEquals(3, quickFindBalanced.getSize("A"));

        quickFindBalanced.union("D", "E");
        assertEquals("D", quickFindBalanced.find("E"));
        assertEquals(2, quickFindBalanced.getSize("D"));

        quickFindBalanced.union("B", "E");
        assertEquals("A", quickFindBalanced.find("E"));
        assertEquals(5, quickFindBalanced.getSize("A"));
    }
}
