package ch.epfl.sweng.string;

import ch.epfl.sweng.base.Node;

/**
 * Simple String node, that can be assigned a label.
 * @author SwengStaff
 *
 */
public class StringNode implements Node {

    private String label;
    private Node left;
    private Node right;

    public StringNode(String newLabel, Node newLeft, Node newRight) {
        left = newLeft;
        right = newRight;
        label = newLabel;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }
}
