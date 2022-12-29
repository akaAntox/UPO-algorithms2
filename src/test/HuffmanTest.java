package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import upo.progdin20035170.Huffman;

public class HuffmanTest {
    @Test
    public void testHuffman() {
        Huffman huffman = new Huffman();
        String text = "testhuffman";
        String encodedText = "00101001100100101111111101011001101";

        assertEquals(encodedText, huffman.encode(text));
    }
}
