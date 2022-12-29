package upo.additionalstructures;

public class Node {
    private Character ch;
    private Integer freq;
    private Node left = null;
    private Node right = null;

    public Node(Character ch, Integer freq) {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(Character ch, Integer freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public Character getCharacter() {
        return ch;
    }

    public Integer getFrequency() {
        return freq;
    }

    public Node getLeftNode() {
        return left;
    }

    public Node getRightNode() {
        return right;
    }

    public void setCharacter(Character ch) {
        this.ch = ch;
    }

    public void setFrequency(Integer freq) {
        this.freq = freq;
    }

    public void setLeftNode(Node left) {
        this.left = left;
    }

    public void setRightNode(Node right) {
        this.right = right;
    }
}