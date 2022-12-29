package upo.progdin20035170;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import upo.additionalstructures.Node;

public class Huffman {
    public String encode(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }

        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray())
            freq.put(c, freq.getOrDefault(c, 0) + 1);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.getFrequency()));
        for (var entry : freq.entrySet())
            pq.add(new Node(entry.getKey(), entry.getValue()));

        while (pq.size() != 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.getFrequency() + right.getFrequency();
            pq.add(new Node(null, sum, left, right));
        }

        Node root = pq.peek();
        Map<Character, String> huffmanCode = new HashMap<>();
        encodeData(root, "", huffmanCode);

        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        return sb.toString();
    }

    private void encodeData(Node root, String str, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }
        if (isLeaf(root)) {
            huffmanCode.put(root.getCharacter(), str.length() > 0 ? str : "1");
        }
        encodeData(root.getLeftNode(), str + '0', huffmanCode);
        encodeData(root.getRightNode(), str + '1', huffmanCode);
    }

    private boolean isLeaf(Node root) {
        return root.getLeftNode() == null && root.getRightNode() == null;
    }
}