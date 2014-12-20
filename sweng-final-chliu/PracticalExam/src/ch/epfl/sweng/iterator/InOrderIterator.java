package ch.epfl.sweng.iterator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

import ch.epfl.sweng.base.Node;
import ch.epfl.sweng.base.Tree;

/**
 * InOrderIterator for a binary tree
 * @author SwengStaff
 *
 */
public class InOrderIterator implements Iterator<Node> {

    private final Tree tree;

    /**
     * Nodes that need to be visited.
     */
    private Stack<Node> nodeStack;

    /**
     * Keeps track of whether we visited the left branch of a node.
     */
    private Map<Node, Boolean> visitedLeftBranch;

    public InOrderIterator(Tree newTree) {
        if (newTree == null) {
            throw new IllegalArgumentException("Cannot iterate a null tree");
        }

        tree = newTree;
        nodeStack = new Stack<Node>();

        nodeStack.push(tree.getRoot());
        visitedLeftBranch = new HashMap<Node, Boolean>();
        visitedLeftBranch.put(tree.getRoot(), false);
    }

    @Override
    public boolean hasNext() {
        return nodeStack.size() > 0;
    }

    @Override
    public Node next() throws NoSuchElementException {
    	if (!hasNext()) {
    		throw new NoSuchElementException("No more elements.");
    	}
        while (hasNext()) {
            Node topOfStack = nodeStack.peek();
            Node leftOfTop = topOfStack.getLeft();
            Node rightOfTop = topOfStack.getRight();

            // first check if we need to go down left branch
            if (visitedLeftBranch.get(topOfStack) == false) {

                // if we haven't, we need to first visit the left branch, if it
                // exists
                if (leftOfTop != null) {
                    nodeStack.push(leftOfTop);
                    visitedLeftBranch.put(leftOfTop, false);
                }

                // left child is now on top of the current node in the stack;
                // next time we see the current node, we don't need to visit the
                // left child
                visitedLeftBranch.put(topOfStack, true);
            } else {

                // if already visited the left child, we can visit the current
                // node; we remove it from the top of the stack, add it's right
                // child to the stack, and then return the node to the caller of
                // next()
                nodeStack.pop();

                if (rightOfTop != null) {
                    nodeStack.push(rightOfTop);
                    visitedLeftBranch.put(rightOfTop, false);
                }

                return topOfStack;
            }
        }

        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove items from immutable trees");
    }
}
