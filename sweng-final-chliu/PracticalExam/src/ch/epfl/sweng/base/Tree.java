package ch.epfl.sweng.base;

import java.util.Iterator;

import ch.epfl.sweng.iterator.InOrderIterator;

/**
 * Class representing an immutable tree.
 * @author SwengStaff
 *
 */
public class Tree implements Iterable<Node> {
    private final Node root;

    public Tree(Node newRoot) {
        if (newRoot == null) {
            throw new IllegalArgumentException("Cannot create a tree with a null root");
        }

        this.root = newRoot;
    }

    public Node getRoot() {
        return root;
    }

    @Override
    public Iterator<Node> iterator() {
        return new InOrderIterator(this);
    }
}
