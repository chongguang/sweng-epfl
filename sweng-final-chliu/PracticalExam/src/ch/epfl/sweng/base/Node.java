package ch.epfl.sweng.base;

/**
 * Abstract class representing nodes in immutable binary trees.
 * The nodes have links to left/right children, and a label for printing.
 * @author SwengStaff
 *
 */
public interface Node {

    Node getLeft();

    Node getRight();

    String getLabel();
}
